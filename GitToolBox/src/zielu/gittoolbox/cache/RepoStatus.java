package zielu.gittoolbox.cache;

import com.google.common.base.Objects;
import git4idea.GitLocalBranch;
import git4idea.GitRemoteBranch;
import git4idea.repo.GitRepository;

public class RepoStatus {
    private static final RepoStatus empty = new RepoStatus(null, null, null, null);

    private final GitLocalBranch branch;
    private final String localHash;
    private final GitRemoteBranch remoteBranch;
    private final String remoteHash;

    private RepoStatus(GitLocalBranch branch, String localHash, GitRemoteBranch remoteBranch, String remoteHash) {
        this.branch = branch;
        this.localHash = localHash;
        this.remoteBranch = remoteBranch;
        this.remoteHash = remoteHash;
    }

    public static RepoStatus create(GitRepository repository) {
        GitLocalBranch branch;
        String localHash = null;
        GitRemoteBranch remote = null;
        String remoteHash = null;

        branch = repository.getCurrentBranch();
        if (branch != null) {
            localHash = branch.getHash();
            remote = branch.findTrackedBranch(repository);
            remoteHash = remote.getHash();
        }
        return new RepoStatus(branch, localHash, remote, remoteHash);
    }

    public static RepoStatus empty() {
        return empty;
    }

    public boolean sameBranch(RepoStatus other) {
        return Objects.equal(branch, other.branch);
    }

    public boolean sameRemoteHash(RepoStatus other) {
        return Objects.equal(remoteHash, other.remoteHash);
    }

    public boolean sameRemoteBranch(RepoStatus other) {
        return Objects.equal(remoteBranch, other.remoteBranch);
    }

    public boolean hasRemoteBranch() {
        return remoteBranch != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RepoStatus that = (RepoStatus) o;
        return Objects.equal(branch, that.branch) &&
            Objects.equal(localHash, that.localHash) &&
            Objects.equal(remoteBranch, that.remoteBranch) &&
            Objects.equal(remoteHash, that.remoteHash);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(branch, localHash, remoteBranch, remoteHash);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .add("branch", branch)
            .add("localHash", localHash)
            .add("remote", remoteBranch)
            .add("remoteHash", remoteHash)
            .toString();
    }
}
