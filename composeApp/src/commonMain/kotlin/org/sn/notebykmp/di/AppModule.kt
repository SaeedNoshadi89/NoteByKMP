package org.sn.notebykmp.di

import org.koin.dsl.module
import org.sn.notebykmp.data.data_sourse.CalendarDataSource
import org.sn.notebykmp.data.data_sourse.CalendarDataSourceImpl
import org.sn.notebykmp.data.data_sourse.LocalDataSource
import org.sn.notebykmp.data.data_sourse.LocalDataSourceImpl
import org.sn.notebykmp.data.repository.AddAndEditNoteRepositoryImpl
import org.sn.notebykmp.data.repository.BookmarkNotesRepositoryImpl
import org.sn.notebykmp.data.repository.NotesRepositoryImpl
import org.sn.notebykmp.domain.gateway.AddAndEditNoteRepository
import org.sn.notebykmp.domain.gateway.BookmarkNotesRepository
import org.sn.notebykmp.domain.gateway.NotesRepository
import org.sn.notebykmp.domain.usecase.AddNoteUseCase
import org.sn.notebykmp.domain.usecase.BookmarkNoteUseCase
import org.sn.notebykmp.domain.usecase.CompleteNoteUseCase
import org.sn.notebykmp.domain.usecase.DeleteNoteUseCase
import org.sn.notebykmp.domain.usecase.GetAllBookmarkNotesUseCase
import org.sn.notebykmp.domain.usecase.GetAllNotesUseCase
import org.sn.notebykmp.domain.usecase.GetCalendarUseCase
import org.sn.notebykmp.domain.usecase.GetNoteByIdUseCase
import org.sn.notebykmp.domain.usecase.SetDateToCalendarUseCase
import org.sn.notebykmp.domain.usecase.UpdateNoteUseCase
import org.sn.notebykmp.presentation.screen.add_note.AddNoteViewModel
import org.sn.notebykmp.presentation.screen.bookmark.BookmarksViewModel
import org.sn.notebykmp.presentation.screen.notes.NotesViewModel

fun appModule() = module {
    factory { Factory().createRoomDatabase().build() }
    single<LocalDataSource> {
        LocalDataSourceImpl(get())
    }
    single<CalendarDataSource> {
        CalendarDataSourceImpl()
    }
    single<BookmarkNotesRepository> {
        BookmarkNotesRepositoryImpl(get())
    }
    single<NotesRepository> {
        NotesRepositoryImpl(get(), get())
    }

    single<AddAndEditNoteRepository> {
        AddAndEditNoteRepositoryImpl(get())
    }

    single { AddNoteUseCase(get()) }
    single { UpdateNoteUseCase(get()) }
    single { GetNoteByIdUseCase(get()) }
    single { GetAllNotesUseCase(get()) }
    single { GetCalendarUseCase(get()) }
    single { SetDateToCalendarUseCase(get()) }
    single { CompleteNoteUseCase(get()) }
    single { DeleteNoteUseCase(get()) }
    single { BookmarkNoteUseCase(get()) }
    single { GetAllBookmarkNotesUseCase(get()) }
    factory { NotesViewModel(get(), get(), get(), get(), get(), get(), get()) }
    factory { AddNoteViewModel(get(), get(), get(), get()) }
    factory { BookmarksViewModel(get()) }
}
