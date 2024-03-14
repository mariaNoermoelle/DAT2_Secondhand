package entities;

public class Item
{
    private int itemId;
    private String author;
    private String title;
    private String body;
    private int price;
    private String authorAddress;

    public Item(int itemId, String author, String title, String body, int price, String authorAddress)
    {
        this.itemId=itemId;
        this.author=author;
        this.title=title;
        this.body=body;
        this.price=price;
        this.authorAddress=authorAddress;
    }

    public int getItemId()
    {
        return itemId;
    }

    public String getAuthor()
    {
        return author;
    }

    public String getTitle()
    {
        return title;
    }

    public String getBody()
    {
        return body;
    }

    public int getPrice()
    {
        return price;
    }

    public String getAuthorAddress()
    {
        return authorAddress;
    }
}
