package nugraha.arief.e_chat.pojo;

/**
 * Created by farhan on 7/9/17.
 */

public class Chat {
    private String id;
    private String name;
    private String chat;

    public Chat(){
    }

    public Chat(String id, String name, String chat){
        this.id = id;
        this.name = name;
        this.chat = chat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}
