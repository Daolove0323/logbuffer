package com.daol.logbuffer.category;

import com.daol.logbuffer._common.api.ListResponse;
import com.daol.logbuffer._common.exception.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public ListResponse<CategoryResponse> getCategories() {
        return ListResponse.of(categoryRepository.findAll().stream()
            .map(CategoryResponse::from)
            .toList());
    }

    @Transactional
    public CategoryResponse createCategory(CategoryRequest categoryReq) {
        Category category = Category.create(categoryReq.name());
        return CategoryResponse.from(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategory(CategoryId id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("ID에 해당하는 카테고리를 찾을 수 없습니다."));
        categoryRepository.delete(category);
    }

    @Transactional
    public void deleteCategoryIfNotUsed(CategoryId id) {
        categoryRepository.deleteCategoryIfNotUsed(id);
    }
}