<script setup lang="ts">
interface Review {
  id: string;
  title: string;
  author: string;
  reviewer: {
    name: string;
    avatar: string;
  };
  rating: number;
  comment: string;
}

const reviews: Review[] = [
  {
    id: '1',
    title: 'Book Review One',
    author: 'Author Name',
    reviewer: {
      name: 'Reviewer1',
      avatar: '/avatar1.jpg'
    },
    rating: 4,
    comment: 'Member rated this book 4 stars'
  },
  {
    id: '2',
    title: 'Book Review Two',
    author: 'Author Name',
    reviewer: {
      name: 'Reviewer2',
      avatar: '/avatar2.jpg'
    },
    rating: 5,
    comment: 'Member rated this book 5 stars'
  },
  {
    id: '2',
    title: 'Book Review Two',
    author: 'Author Name',
    reviewer: {
      name: 'Reviewer2',
      avatar: '/avatar2.jpg'
    },
    rating: 5,
    comment: 'Member rated this book 5 stars'
  },
  {
    id: '2',
    title: 'Book Review Two',
    author: 'Author Name',
    reviewer: {
      name: 'Reviewer2',
      avatar: '/avatar2.jpg'
    },
    rating: 5,
    comment: 'Member rated this book 5 stars'
  }
  // Add more review objects as needed
];

const getRatingStars = (rating: number) => {
  return '★'.repeat(rating) + '☆'.repeat(5 - rating);
};
</script>

<template>
  <div class="max-w-7xl mx-auto p-4">
    <h1 class="text-2xl font-bold mb-6 text-gray-800">Latest Reviews</h1>
    
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
      <div v-for="review in reviews" 
           :key="review.id" 
           class="bg-white rounded-lg shadow-md p-4 hover:shadow-lg transition-shadow duration-200">
        <h2 class="text-xl font-semibold mb-2">{{ review.title }}</h2>
        
        <p class="text-gray-600 italic mb-4">
          {{ review.comment }}
        </p>
        
        <div class="flex items-center mb-4">
          <img :src="review.reviewer.avatar" 
               :alt="review.reviewer.name"
               class="w-10 h-10 rounded-full mr-3" />
          
          <div>
            <div class="font-medium">{{ review.reviewer.name }}</div>
            <div class="text-yellow-400">{{ getRatingStars(review.rating) }}</div>
            <div class="text-sm text-gray-500">
              Rated {{ review.rating }} stars
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>