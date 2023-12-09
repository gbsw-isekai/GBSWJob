package kr.hs.gbsw.gbswjob.company.repository

import kr.hs.gbsw.gbswjob.company.domain.Company
import kr.hs.gbsw.gbswjob.company.domain.CompanyComment
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyCommentRepository : JpaRepository<CompanyComment, Int> {
    fun findByCompany(companyId: Company?): List<CompanyComment>
}