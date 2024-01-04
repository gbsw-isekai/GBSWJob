package kr.hs.gbsw.gbswjob.company.dto

class CompanyListGetDto(
    var id: Int, //회사
    var name: String, //회사 명
    var postalCode: String, //우편번호
    var address: String, //주소
    var industryCode: Int, //업종코드
    var industry: String, //업종명
    var registrationNumber: Long, //사업자 등록번호
    var viewCount: Long, //조회수
    var employeeCount: Long?,
    var averageYearPrice: Long?, //
//    var companyNpsEmployeeData: MutableList<CompanyNpsEmployeeData>?
) {
}

//class 붕어빵(
//    var 반죽: 반죽,
//    var 소: 소
//)
//
//var 붕어빵 인스턴스 = new 붕어빵(밀가루반죽, 팥소)
