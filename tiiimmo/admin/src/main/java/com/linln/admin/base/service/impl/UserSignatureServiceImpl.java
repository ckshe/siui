package com.linln.admin.base.service.impl;

import com.linln.admin.base.domain.UserSignature;
import com.linln.admin.base.repository.UserSignatureRepository;
import com.linln.admin.base.service.UserSignatureService;
import com.linln.common.data.PageSort;
import com.linln.common.enums.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author www
 * @date 2020/09/14
 */
@Service
public class UserSignatureServiceImpl implements UserSignatureService {

    @Autowired
    private UserSignatureRepository userSignatureRepository;

    /**
     * 根据ID查询数据
     * @param id 主键ID
     */
    @Override
    @Transactional
    public UserSignature getById(Long id) {
        return userSignatureRepository.findById(id).orElse(null);
    }

    /**
     * 获取分页列表数据
     * @param example 查询实例
     * @return 返回分页数据
     */
    @Override
    public Page<UserSignature> getPageList(Example<UserSignature> example) {
        // 创建分页对象
        PageRequest page = PageSort.pageRequest();
        return userSignatureRepository.findAll(example, page);
    }

    /**
     * 保存数据
     * @param userSignature 实体对象
     */
    @Override
    public UserSignature save(UserSignature userSignature) {
        return userSignatureRepository.save(userSignature);
    }

    /**
     * 状态(启用，冻结，删除)/批量状态处理
     */
    @Override
    @Transactional
    public Boolean updateStatus(StatusEnum statusEnum, List<Long> idList) {
        return userSignatureRepository.updateStatus(statusEnum.getCode(), idList) > 0;
    }

    @Override
    public UserSignature findByCardSequenceAndType(String cardSequence, String type) {
        return userSignatureRepository.findAllByCardSequenceAndType(cardSequence,type);
    }
}