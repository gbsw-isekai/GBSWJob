package kr.hs.gbsw.gbswjob.company.dto

import kr.hs.gbsw.gbswjob.company.domain.CompanyComment
import kr.hs.gbsw.gbswjob.company.domain.CompanyReview
import kr.hs.gbsw.gbswjob.company.domain.CompanyViews

class CompanyGetDto(
    var id: Int?,
    var name: String,
    var stack: String,
    var grade: Double,
    var averageSalary: Int,
    var comments: MutableList<CompanyComment>?,
    var reviews: MutableList<CompanyReview>?,
    var viewsCount: Long
) {
}

//class 붕어빵(
//    var 반죽: 반죽,
//    var 소: 소
//)
//
//var 붕어빵 인스턴스 = new 붕어빵(밀가루반죽, 팥소)
