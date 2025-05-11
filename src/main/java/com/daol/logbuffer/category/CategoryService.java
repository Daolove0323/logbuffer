package com.daol.logbuffer.category;

import com.daol.logbuffer._common.api.ListResponse;
import java.util.List;
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
        Category category = categoryRepository.findById(id).orElseThrow();
        categoryRepository.delete(category);
    }
}
