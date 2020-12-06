package kz.iitu.end;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private Double price;

    public Book(){}

//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "book_genres",
//            joinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "genre_id", referencedColumnName = "id")}
//    )
//    private List<Genre> genres = new ArrayList<>();
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinTable(name = "book_authors",
//            joinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "author_id", referencedColumnName = "id")}
//    )
//    private List<Author> authors = new ArrayList<>();

    public Book(String title, Double price)
    {
        this.title = title;
        this.price = price;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title +
                ", price='" + price ;
    }
}
