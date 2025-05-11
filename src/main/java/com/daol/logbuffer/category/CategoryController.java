package com.daol.logbuffer.category;

import com.daol.logbuffer._common.api.ApiResponse;
import com.daol.logbuffer._common.api.ListResponse;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ListResponse<CategoryResponse>> getCategories() {
        return ApiResponse.ok(categoryService.getCategories());
    }

    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(
        @RequestBody CategoryRequest categoryReq
    ) {
        return ApiResponse.created(categoryService.createCategory(categoryReq));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(new CategoryId(categoryId));
        return ApiResponse.noContent();
    }
}
