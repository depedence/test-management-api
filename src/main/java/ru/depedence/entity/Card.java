package ru.depedence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.depedence.entity.dto.CardDto;
import ru.depedence.entity.dto.response.CardStatus;

@Entity
@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title", nullable = false)
    @Size(max = 128)
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private CardStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    public Card(String title, String content, CardStatus status) {
        this.title = title;
        this.content = content;
        this.status = status;
    }

    public CardDto toDto() {
        return new CardDto(id, title, content, status);
    }

}