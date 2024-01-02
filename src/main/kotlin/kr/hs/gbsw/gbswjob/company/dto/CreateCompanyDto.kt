package kr.hs.gbsw.gbswjob.company.dto

class CreateCompanyDto(
    var id: Int,
    var name: String,
    var postalCode: String,
    var address: String,
    var industryCode: Int,
    var industry: Int,
    var registrationNumber: Long
) {

}