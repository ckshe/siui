package com.linln.admin.base.repository;

import com.linln.admin.base.domain.UserSignature;
import com.linln.modules.system.domain.User;
import com.linln.modules.system.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author www
 * @date 2020/09/14
 */
public interface UserSignatureRepository extends BaseRepository<UserSignature, Long> {

    @Query(value = "select * from base_user_signature where card_sequence =?1 and type = ?2",nativeQuery = true)
    UserSignature findAllByCardSequenceAndType(String cardSequence,String type);
}