package kz.witme.project.dashboard

import kotlinx.collections.immutable.persistentListOf

class MockBooksProvider {
    val mockBooks = persistentListOf(
        Book(
            name = "Atomic Habits",
            author = "James Clear",
            totalDuration = 320,
            description = "A guide to building good habits and breaking bad ones.",
            imageUri = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",
            
            bookStatus = BookStatus.READING,
            planningDate = "01.08.2024",
            endDate = null,
            startDate = "01.08.2024",
            rating = null,
            rateDescription = null,
            avgEmoji = 5
        ),
        Book(
            name = "1984",
            author = "George Orwell",
            totalDuration = 420,
            description = "A dystopian novel set in a totalitarian society under constant surveillance.",
            imageUri = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",
            
            bookStatus = BookStatus.READING,
            planningDate = null,
            endDate = "10.05.2024",
            startDate = "20.04.2024",
            rating = 4,
            rateDescription = "Thought-provoking and chilling",
            avgEmoji = 4
        ),
        Book(
            name = "The Lean Startup",
            author = "Eric Ries",
            totalDuration = 250,
            description = "An approach to managing startups with lean principles.",
            imageUri = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",
            
            bookStatus = BookStatus.READING,
            planningDate = "15.09.2024",
            endDate = null,
            startDate = null,
            rating = null,
            rateDescription = null,
            avgEmoji = 3
        ),
        Book(
            name = "Pride and Prejudice",
            author = "Jane Austen",
            totalDuration = 480,
            description = "A classic novel about love and social standing.",
            imageUri = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",
            
            bookStatus = BookStatus.READING,
            planningDate = null,
            endDate = "25.03.2024",
            startDate = "05.03.2024",
            rating = 5,
            rateDescription = "Romantic and witty",
            avgEmoji = 4
        ),
        Book(
            name = "Educated",
            author = "Tara Westover",
            totalDuration = 360,
            description = "A memoir about overcoming adversity through education.",
            imageUri = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",
            
            bookStatus = BookStatus.READING,
            planningDate = "01.08.2024",
            endDate = null,
            startDate = "01.08.2024",
            rating = null,
            rateDescription = null,
            avgEmoji = 5
        ),
        Book(
            name = "To Kill a Mockingbird",
            author = "Harper Lee",
            totalDuration = 450,
            description = "A story of racial injustice and moral growth in the American South.",
            imageUri = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",
            
            bookStatus = BookStatus.READING,
            planningDate = "10.10.2024",
            endDate = null,
            startDate = "01.10.2024",
            rating = 5,
            rateDescription = "Deeply moving",
            avgEmoji = 4
        ),
        Book(
            name = "Sapiens: A Brief History of Humankind",
            author = "Yuval Noah Harari",
            totalDuration = 400,
            description = "An exploration of the history of humankind.",
            imageUri = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",
            
            bookStatus = BookStatus.READING,
            planningDate = null,
            endDate = "15.08.2024",
            startDate = "25.07.2024",
            rating = 4,
            rateDescription = "Informative and eye-opening",
            avgEmoji = 4
        ),
        Book(
            name = "The Alchemist",
            author = "Paulo Coelho",
            totalDuration = 220,
            description = "A novel about following one's dreams and discovering destiny.",
            imageUri = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",
            
            bookStatus = BookStatus.READING,
            planningDate = null,
            endDate = "10.09.2024",
            startDate = "01.09.2024",
            rating = 5,
            rateDescription = "Inspiring and magical",
            avgEmoji = 5
        ),
        Book(
            name = "Clean Code",
            author = "Robert C. Martin",
            totalDuration = 500,
            description = "A handbook of agile software craftsmanship.",
            imageUri = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",
            
            bookStatus = BookStatus.READING,
            planningDate = "01.12.2024",
            endDate = null,
            startDate = "15.11.2024",
            rating = null,
            rateDescription = null,
            avgEmoji = 3
        ),
        Book(
            name = "The Catcher in the Rye",
            author = "J.D. Salinger",
            totalDuration = 280,
            description = "A story of teenage rebellion and identity.",
            imageUri = "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png",
            
            bookStatus = BookStatus.READING,
            planningDate = "01.11.2024",
            endDate = null,
            startDate = null,
            rating = null,
            rateDescription = null,
            avgEmoji = 3
        )
    )
}