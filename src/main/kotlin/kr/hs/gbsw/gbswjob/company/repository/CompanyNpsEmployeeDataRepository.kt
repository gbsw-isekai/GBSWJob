package kr.hs.gbsw.gbswjob.company.repository

import kr.hs.gbsw.gbswjob.company.domain.CompanyNpsEmployeeData
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyNpsEmployeeDataRepository: JpaRepository<CompanyNpsEmployeeData, Int> {
    fun findAllByCompanyId(companyId: Int): List<CompanyNpsEmployeeData>
}