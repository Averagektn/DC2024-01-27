package by.haritonenko.jpa.facade;

import by.haritonenko.jpa.dto.request.CreateAuthorDto;
import by.haritonenko.jpa.dto.request.UpdateAuthorDto;
import by.haritonenko.jpa.dto.response.AuthorResponseDto;
import by.haritonenko.jpa.mapper.AuthorMapper;
import by.haritonenko.jpa.model.Author;
import by.haritonenko.jpa.service.AuthorService;
import by.haritonenko.jpa.util.dto.author.request.CreateAuthorDtoTestBuilder;
import by.haritonenko.jpa.util.dto.author.request.UpdateAuthorDtoTestBuilder;
import by.haritonenko.jpa.util.dto.author.response.AuthorResponseDtoTestBuilder;
import by.haritonenko.jpa.util.entity.AuthorTestBuilder;
import by.haritonenko.jpa.util.entity.DetachedAuthorTestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorFacadeTest {

    @InjectMocks
    private AuthorFacade authorFacade;

    @Mock
    private AuthorService authorService;

    @Mock
    private AuthorMapper authorMapper;

    @Test
    @DisplayName("Test should return expected response")
    void findById() {
        final Author persistedAuthor = AuthorTestBuilder.author().build();
        final AuthorResponseDto expected = AuthorResponseDtoTestBuilder.author().build();

        doReturn(persistedAuthor)
                .when(authorService)
                .findById(persistedAuthor.getId());

        doReturn(expected)
                .when(authorMapper)
                .toAuthorResponse(persistedAuthor);

        final AuthorResponseDto actual = authorFacade.findById(expected.getId());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void findAll() {
        final Author persistedAuthor = AuthorTestBuilder.author().build();
        final AuthorResponseDto expectedAuthor = AuthorResponseDtoTestBuilder.author().build();
        final List<AuthorResponseDto> expected = List.of(expectedAuthor);

        doReturn(List.of(persistedAuthor))
                .when(authorService)
                .findAll();

        doReturn(expectedAuthor)
                .when(authorMapper)
                .toAuthorResponse(persistedAuthor);

        final List<AuthorResponseDto> actual = authorFacade.findAll();

        assertThat(actual).isEqualTo(expected);

    }

    @Test
    @DisplayName("Test should return expected response")
    void save() {
        final Author detachedAuthor = DetachedAuthorTestBuilder.author().build();
        final CreateAuthorDto request = CreateAuthorDtoTestBuilder.author().build();
        final Author persistedAuthor = AuthorTestBuilder.author().build();
        final AuthorResponseDto expected = AuthorResponseDtoTestBuilder.author().build();

        doReturn(detachedAuthor)
                .when(authorMapper)
                .toAuthor(request);

        doReturn(persistedAuthor)
                .when(authorService)
                .save(detachedAuthor);

        doReturn(expected)
                .when(authorMapper)
                .toAuthorResponse(persistedAuthor);

        final AuthorResponseDto actual = authorFacade.save(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should return expected response")
    void update() {
        final UpdateAuthorDto request = UpdateAuthorDtoTestBuilder.author().build();
        final Author persistedAuthor = AuthorTestBuilder.author().build();
        final AuthorResponseDto expected = AuthorResponseDtoTestBuilder.author().build();

        doReturn(persistedAuthor)
                .when(authorService)
                .findById(persistedAuthor.getId());

        doReturn(persistedAuthor)
                .when(authorMapper)
                .toAuthor(request, persistedAuthor);

        doReturn(persistedAuthor)
                .when(authorService)
                .update(persistedAuthor);

        doReturn(expected)
                .when(authorMapper)
                .toAuthorResponse(persistedAuthor);

        final AuthorResponseDto actual = authorFacade.update(request);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test should complete successfully for provided id")
    void delete() {
        final Long authorId = 1L;

        doNothing().when(authorService)
                .deleteById(authorId);

        authorFacade.delete(authorId);

        verify(authorService, times(1)).deleteById(authorId);
    }
}