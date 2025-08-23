package CodersBay.Kino.cinema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "cinemas")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cinema {
    @Id
    private String cinemaId;
    private String name;
    private String address;
    private String manager;
    private int maxHalls;
    // List of hall IDs belonging to this cinema
    private List<String> hallIds;

    public Cinema(String name, String address,String manager, int maxHalls) {
        this.name = name;
        this.address = address;
        this.manager = manager;
        this.maxHalls = maxHalls;
    }
}
