<script setup lang="ts">
import { ref } from 'vue';
import Rules from '@/components/admin/Rules.vue';
import Tiptap from '@/components/common/Tiptap.vue';

const selectedCategories = ref<string[]>([]);
const categories = ref([
    { value: '1', label: 'Fantasy' },
    { value: '2', label: 'Romance' },
    { value: '3', label: 'Action' },
    { value: '4', label: 'Mystery' }
]);

const isDropdownOpen = ref(false);

const toggleCategory = (category: string) => {
    const selectedCategory = categories.value.find(cat => cat.value === category)?.label;
    const index = selectedCategories.value.indexOf(selectedCategory || '');
    if (index === -1) {
        selectedCategories.value.push(selectedCategory || '');
    } else {
        selectedCategories.value.splice(index, 1);
    }
};
const getCategoryIds = () => {
    return selectedCategories.value.map(categoryName => {
        const category = categories.value.find(cat => cat.label === categoryName);
        return category?.value;
    });
};
const saveNovel = async () => {
    const categoryIds = getCategoryIds();
    const novelData = {
        title: "Tiêu đề tiểu thuyết",
        author: "Tác giả",
        image: "image-url",
        description: "Mô tả tiểu thuyết",
        categories: categoryIds, // Gửi id của các thể loại
    };
};

const isSelected = (category: string) => {
    const selectedCategory = categories.value.find(cat => cat.value === category)?.label;
    return selectedCategories.value.includes(selectedCategory || '');
};
const selectedImage = ref<File | null>(null);
const imageUrl = ref('');         // Lưu trữ URL của hình ảnh đã chọn

// Hàm xử lý khi chọn ảnh
const handleImageChange = (event) => {
    const file = event.target.files[0];
    if (file) {
        selectedImage.value = file;
        imageUrl.value = URL.createObjectURL(file);  // Tạo URL để hiển thị ảnh
    }
};

const form = ref({
    content: '',
})
const submitForm = () => {

}
</script>

<template>
    <!-- Parent container to wrap both the form and section -->
    <div class="flex flex-col lg:flex-row gap-4">
        <!-- Form Section -->
        <div class="bg-[#F8F8F7] border border-gray-300 rounded-lg p-4 shadow-md flex-1">
            <div class=" font-semibold text-xl text-gray-800 mb-2">
                Thông báo
            </div>
            <div class="text-gray-700">
                <p><strong>Lưu ý:</strong> Để đăng truyện do bạn sáng tác, vui lòng đọc <a>Hướng dẫn tại đấy</a>.</p>
            </div>
            <form class="my-5" @submit.prevent="submitForm">
                <!-- Title -->
                <div class="md:col-span-1 my-4">
                    <label for="floating_title" class="block text-sm font-medium text-gray-700">Tiêu đề tiểu
                        thuyết</label>
                    <input type="text" name="floating_title" id="floating_title"
                        class="bg-white mt-1 block w-full p-2 border rounded-md text-sm text-gray-900 bg-transparent border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required />
                </div>
                <!-- Author -->
                <div class="md:col-span-1 my-4">
                    <label for="floating_author" class="block text-sm font-medium text-gray-700">Tác giả</label>
                    <input type="text" name="floating_author" id="floating_author"
                        class="bg-white mt-1 block w-full p-2 border rounded-md text-sm text-gray-900 bg-transparent border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        required />
                </div>

                <!-- Image -->
                <div class="space-y-4 ">
                    <!-- Label -->
                    <label for="file_input" class="block text-sm font-medium text-gray-800 dark:text-gray-200">
                        Upload File
                    </label>

                    <!-- File Input -->
                    <div class="relative">
                        <input type="file" id="file_input" @change="handleImageChange"
                            class="bg-white block w-full text-sm text-gray-900 border rounded-lg cursor-pointer bg-gray-50 focus:ring-2 focus:ring-blue-500 focus:border-blue-500 dark:text-gray-400 dark:bg-gray-800 dark:border-gray-600 dark:focus:ring-blue-400 dark:focus:border-blue-400 file:mr-4 file:py-2 file:px-4 file:rounded file:border-0 file:text-sm file:font-medium file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100" />
                    </div>

                    <!-- Helper Text -->
                    <p class="text-sm text-gray-500 dark:text-gray-400">
                        SVG, PNG, JPG, or GIF (MAX. 800x400px)
                    </p>
                    <!-- Preview Image -->
                    <div v-if="selectedImage" class="mt-4">
                        <div
                            class="relative w-32 h-32 overflow-hidden rounded-lg border border-gray-300 shadow-sm dark:border-gray-600">
                            <img :src="imageUrl" alt="Selected image" class="object-cover w-full h-full" />
                        </div>
                    </div>
                </div>

                <!-- Category -->
                <div class="md:col-span-1 my-4 relative">
                    <label for="floating_category" class="block text-sm font-medium text-gray-700">Chọn thể loại</label>
                    <input type="text" readonly :value="selectedCategories.join(', ')"
                        class="bg-white mt-1 block w-full p-2 border rounded-md text-sm text-gray-900 bg-transparent border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
                        @click="isDropdownOpen = !isDropdownOpen" />
                    <div v-if="isDropdownOpen"
                        class="absolute z-10 bg-white border border-gray-300 rounded-md mt-1 shadow-md max-h-60 overflow-y-auto w-full">
                        <div v-for="category in categories" :key="category.value"
                            class="p-2 cursor-pointer hover:bg-gray-100 text-sm"
                            @click="toggleCategory(category.value)">
                            <input type="checkbox" :id="category.value" :value="category.value"
                                :checked="isSelected(category.value)" class="mr-2" />
                            {{ category.label }}
                        </div>
                    </div>
                </div>

                <Tiptap v-model="form.content" />
                <!-- Submit -->
                <div class="flex justify-end my-4">
            <button
                class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300">
                Submit
            </button>
        </div>
            </form>
        </div>

        <!-- Quy định đăng truyện Section -->
        <div class="bg-[#F8F8F7] border border-gray-300 rounded-lg p-4 shadow-md flex-1">
            <div class="font-semibold text-xl text-gray-800 mb-2">
                QUY ĐỊNH ĐĂNG TRUYỆN
            </div>
            <div class="text-gray-700">
                <Rules />
            </div>
        </div>
    </div>
</template>
