package CodersBay.Kino.cinema;

import CodersBay.Kino.hall.Hall;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cinemaId;
    private String name;
    private String address;
    private String manager;
    private int maxHalls;
    @OneToMany(mappedBy = "cinema")
    private List<Hall> hallsList;

    public Cinema(String name, String address,String manager, int maxHalls) {
        this.name = name;
        this.address = address;
        this.manager = manager;
        this.maxHalls = maxHalls;
    }
}
