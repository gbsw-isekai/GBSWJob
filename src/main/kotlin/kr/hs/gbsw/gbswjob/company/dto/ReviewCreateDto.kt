package kr.hs.gbsw.gbswjob.company.dto

class ReviewCreateDto(
    var title: String,
    var pro: String,
    var con: String,
    var welfareAndSalaryRating: Double, //복지&급여
    var atmosphereRating: Double, //분위기
    var workloadRating: Double, //업무 정도/강도
    var transportationRating: Double, //교통
) {

}