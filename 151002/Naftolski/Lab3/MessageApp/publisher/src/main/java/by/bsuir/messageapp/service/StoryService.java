package by.bsuir.messageapp.service;

import by.bsuir.messageapp.dao.repository.StoryRepository;
import by.bsuir.messageapp.model.entity.Story;
import by.bsuir.messageapp.model.request.StoryRequestTo;
import by.bsuir.messageapp.model.response.StoryResponseTo;
import by.bsuir.messageapp.service.exceptions.ResourceNotFoundException;
import by.bsuir.messageapp.service.exceptions.ResourceStateException;
import by.bsuir.messageapp.service.mapper.StoryMapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@RequiredArgsConstructor
public class StoryService implements IService<StoryRequestTo, StoryResponseTo> {
    private final StoryRepository storyRepository;
    private final StoryMapper storyMapper;

    @Override
    public StoryResponseTo findById(Long id) {
        return storyRepository.findById(id).map(storyMapper::getResponse).orElseThrow(() -> findByIdException(id));
    }

    @Override
    public List<StoryResponseTo> findAll() {
        return storyMapper.getListResponse(storyRepository.findAll());
    }

    @Override
    public StoryResponseTo create(StoryRequestTo request) {
        StoryResponseTo response = storyMapper.getResponse(storyRepository.save(storyMapper.getStory(request)));

        if (response == null) {
            throw createException();
        }

        return response;
    }

    @Override
    public StoryResponseTo update(StoryRequestTo request) {
        Story story = storyMapper.getStory(request);
        story = storyRepository.save(story);
        StoryResponseTo response = storyMapper.getResponse(story);

//        StoryResponseTo response = storyMapper.getResponse(storyRepository.save(storyMapper.getStory(request)));
        if (response == null) {
            throw updateException();
        }

        return response;
    }

    @Override
    public void removeById(Long id) {
        Story story = storyRepository.findById(id).orElseThrow(StoryService::removeException);

        storyRepository.delete(story);
    }

    private static ResourceNotFoundException findByIdException(Long id) {
        return new ResourceNotFoundException(HttpStatus.NOT_FOUND.value() * 100 + 41, "Can't find story by id = " + id);
    }

    private static ResourceStateException createException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 42, "Can't create story");
    }

    private static ResourceStateException updateException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 43, "Can't update story");
    }

    private static ResourceStateException removeException() {
        return new ResourceStateException(HttpStatus.CONFLICT.value() * 100 + 44, "Can't remove story");
    }
}
