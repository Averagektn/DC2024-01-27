package by.bsuir.poit.dc.cassandra.api.controllers;

import by.bsuir.poit.dc.cassandra.api.dto.request.UpdateNoteDto;
import by.bsuir.poit.dc.cassandra.api.dto.response.NoteDto;
import by.bsuir.poit.dc.cassandra.services.NoteService;
import by.bsuir.poit.dc.dto.groups.Create;
import by.bsuir.poit.dc.dto.groups.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Paval Shlyk
 * @since 01/02/2024
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1.0/notes")
public class WebNoteController {
    private final NoteService noteService;

    @PostMapping
    public ResponseEntity<NoteDto> createNewsNote(
	@RequestBody @Validated(Create.class) UpdateNoteDto dto) {
	NoteDto response = noteService.save(dto);
	return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<NoteDto>> getNotes() {
	List<NoteDto> list = noteService.getAll();
	return ResponseEntity.ok(list);
    }

    @GetMapping("/{noteId}")
    public NoteDto getNoteById(
	@PathVariable long noteId) {
	return noteService.getById(noteId);
    }

    @PutMapping
    public NoteDto updateNoteById(
	@RequestBody @Validated(Update.class) UpdateNoteDto dto) {
	long noteId = dto.id();
	return noteService.update(noteId, dto);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<?> deleteNoteById(
	@PathVariable long noteId) {
	var status = noteService.delete(noteId).isPresent()
			 ? HttpStatus.NO_CONTENT
			 : HttpStatus.NOT_FOUND;
	return ResponseEntity.status(status).build();
    }

}
