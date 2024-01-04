package kr.hs.gbsw.gbswjob.company.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import org.hibernate.annotations.Where

@Entity
@Where(clause = """
    industry like '%정보%' or
    industry like '%소프트웨어%' or
    industry like '%통신%'
""")
class Company(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int, //회사
    var name: String, //회사 명
    var postalCode: String, //우편번호
    var address: String, //주소
    var industryCode: Int, //업종코드
    var industry: String, //업종명
    var registrationNumber: Long, //사업자 등록번호
    var viewCount: Long, //조회수

    var latestEmployeeCount: Long?, // 최신 직원수
    var latestAverageSalary: Long?, // 최신 평균 월급

    @OneToMany(mappedBy = "company")
    var companyNpsEmployeeData: MutableList<CompanyNpsEmployeeData>?,
    @OneToMany(mappedBy = "company")
    var comments: MutableList<CompanyComment>?,
    @OneToMany(mappedBy = "company")
    var reviews: MutableList<CompanyReview>?,
    @JsonIgnore
    @OneToMany(mappedBy = "company")
    var views: MutableList<CompanyView>?
) {
}