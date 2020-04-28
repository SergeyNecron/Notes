package ru.notes.model

import javax.persistence.*

@Entity
//syntax error at or near "user"
@Table(name = "USER_NOTE")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = -1,
        var name: String? = null,
        var email: String? = null,
        var password: String? = null,
        var enabled: Boolean = true,
        @OneToMany(mappedBy = "user",
                fetch = FetchType.LAZY,
                cascade = [CascadeType.ALL],
                orphanRemoval = true)
        var notes: Set<Note>
)
