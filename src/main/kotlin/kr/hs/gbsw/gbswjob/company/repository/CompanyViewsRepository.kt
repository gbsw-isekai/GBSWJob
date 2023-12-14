package kr.hs.gbsw.gbswjob.company.repository

import kr.hs.gbsw.gbswjob.company.domain.CompanyViews
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyViewsRepository : JpaRepository<CompanyViews, Int> {

}