package kr.hs.gbsw.gbswjob.company.dto

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

class CompanyGetDto(
    var id: Int, //회사
    var name: String, //회사 명
    var postalCode: String, //우편번호
    var address: String, //주소
    var industryCode: Int, //업종코드
    var industry: String, //업종명
    var registrationNumber: Long, //사업자 등록번호
    var viewCount: Long,
    var averageYearPrice: Long, //1년간 총 월급평균

//    var januaryPrice: Long,
//    var februaryPrice: Long,
//    var marchPrice: Long,
//    var aprilPrice: Long,
//    var mayPrice: Long,
//    var junePrice: Long,
//    var julyPrice: Long,
//    var septemberPrice: Long,
//    var octoberPrice: Long,
//    var novemberPrice: Long,
//    var decemberPrice: Long,

) {
}