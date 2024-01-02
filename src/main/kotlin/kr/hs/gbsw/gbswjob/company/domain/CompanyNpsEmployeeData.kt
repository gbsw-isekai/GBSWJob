package kr.hs.gbsw.gbswjob.company.domain

import jakarta.persistence.*

@Entity
class CompanyNpsEmployeeData(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int, //고유번호

    var year: Int, //년
    var month: Int, //월
    var enter: Int, //입사자
    var quit: Int, //퇴사자
    var total: Int, //총 직원수
    var monthlyPrice: Long, //국민 연금 보험료 / 0.09 /총 직원수 = 평균 월 급

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    var company: Company
) {
}