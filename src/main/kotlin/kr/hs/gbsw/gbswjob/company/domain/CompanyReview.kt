package kr.hs.gbsw.gbswjob.company.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import kr.hs.gbsw.gbswjob.user.domain.User

@Entity
class CompanyReview(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int?,
    var title: String, //제목
    @Column(columnDefinition = "TEXT")
    var pro: String, //장점
    @Column(columnDefinition = "TEXT")
    var con: String, //단점
    var welfareAndSalaryRating: Double, //복지&급여
    var atmosphereRating: Double, //분위기
    var workloadRating: Double, //업무 정도/강도
    var transportationRating: Double, //교통

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @JsonIgnore
    var company: Company,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User
) {
    fun calculateAverageRating(): Double {
        var grade = 0.0
        grade += welfareAndSalaryRating
        grade += atmosphereRating
        grade += workloadRating
        grade += transportationRating

        return grade / 4.0
    }
}