package HelperClasses;

/**
 * Created by ElessarST on 14.02.2015.
 */
public enum FriendshipStatus {
    DECLINED(-1L), SENDED(0), ACCEPTED(1);

    private Long status;

    FriendshipStatus(long l) {
        this.status = l;
    }

    public Long getStatus(){
        return status;
    }
}
