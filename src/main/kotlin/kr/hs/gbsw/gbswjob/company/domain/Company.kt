package kr.hs.gbsw.gbswjob.company.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany

@Entity
class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,
    var name: String,
    var stack: String,
    var averageSalary: Int,
    @OneToMany(mappedBy = "company")
    var comments: MutableList<CompanyComment>?,
    @OneToMany(mappedBy = "company")
    var reviews: MutableList<CompanyReview>?,
    @JsonIgnore
    @OneToMany(mappedBy = "company")
    var views: MutableList<CompanyViews>?,
    var viewCount: Long
) {
    fun calculateAverageRating(): Double {
        if (reviews.isNullOrEmpty()) {
            return 0.0
        }

        val ratingSum = reviews?.sumOf {
            it.calculateAverageRating()
        } ?: 0.0

        return ratingSum / reviews!!.size
    }
}