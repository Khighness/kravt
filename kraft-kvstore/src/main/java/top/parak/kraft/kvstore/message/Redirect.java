package top.parak.kraft.kvstore.message;

/**
 * @author KHighness
 * @since 2022-03-14
 * @email parakovo@gmail.com
 */
public class Redirect {

    private final String leaderId;

    public Redirect(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getLeaderId() {
        return leaderId;
    }

    @Override
    public String toString() {
        return "Redirect{" +
                "leaderId='" + leaderId + '\'' +
                '}';
    }

}
