package org.techcaresinc.techcaresinc_obr_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.techcaresinc.techcaresinc_obr_service.entities.Certificate;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
