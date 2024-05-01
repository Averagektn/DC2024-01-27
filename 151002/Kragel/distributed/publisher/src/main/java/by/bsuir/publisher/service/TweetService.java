package by.bsuir.publisher.service;

import by.bsuir.publisher.dto.request.TweetRequestDto;
import by.bsuir.publisher.dto.response.TweetResponseDto;
import by.bsuir.publisher.exception.ResourceNotFoundException;
import by.bsuir.publisher.model.Tweet;
import by.bsuir.publisher.repository.TweetRepository;
import by.bsuir.publisher.service.mapper.TweetMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TweetService {
    private final TweetRepository tweetRepository;
    private final TweetMapper tweetMapper;

    @Transactional(readOnly = true)
    public List<TweetResponseDto> getAll() {
        return tweetMapper.toDto(tweetRepository.findAll());
    }

    @CacheEvict(value = "tweet", key = "#id")
    public void deleteById(Long id) {
        Tweet entity = tweetRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet with id = " + id + " is not found"));
        tweetRepository.delete(entity);
    }

    @CachePut(value = "tweet", key = "#result.id()")
    public TweetResponseDto create(TweetRequestDto dto) {
        Tweet newEntity = tweetMapper.toEntity(dto);
        return tweetMapper.toDto(tweetRepository.save(newEntity));
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "tweet", key = "#id")
    public TweetResponseDto getById(Long id) {
        Tweet entity = tweetRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tweet with id = " + id + " is not found"));
        return tweetMapper.toDto(entity);
    }

    @CachePut(value = "tweet", key = "#result.id()")
    public TweetResponseDto update(TweetRequestDto dto) {
        Tweet entity = tweetRepository
                .findById(dto.id())
                .orElseThrow(() -> new ResourceNotFoundException("Tweet with id = " + dto.id() + " is not found"));
        final Tweet updated = tweetMapper.partialUpdate(dto, entity);
        return tweetMapper.toDto(tweetRepository.save(updated));
    }
}
