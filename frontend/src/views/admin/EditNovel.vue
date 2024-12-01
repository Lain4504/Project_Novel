<template>
    <div class="flex justify-center items-center max-w-7xl mx-auto">
        <!-- Nội dung chính -->
        <div class="bg-white p-8 rounded-lg shadow-md w-full">
            <p class="text-lg font-bold">Novel Management</p>
            <div class="space-y-4 mt-10">
                <div class="md:col-span-1">
                    <!-- Title -->
                    <p class="text-lg font-bold cursor-pointer" @click="toggleDropdownNovel">Urasekai Picnic</p>
                    <!-- Dropdown Menu -->
                    <div v-if="isDropdownVisible"
                        class="absolute z-10 bg-white border border-gray-300 rounded-md mt-2 w-36 shadow-md">
                        <div class="p-2 cursor-pointer hover:bg-gray-100 text-sm" @click="handleOptionClick('Tập')">Tập
                        </div>
                        <div class="p-2 cursor-pointer hover:bg-gray-100 text-sm"
                            @click="handleOptionClick('Sắp xếp tập')">Sắp xếp tập</div>
                        <div class="p-2 cursor-pointer hover:bg-gray-100 text-sm"
                            @click="handleOptionClick('Xóa novel')">Xóa novel</div>
                    </div>
                </div>
                <div>
                    <ul class="space-y-4 text-gray-700">
                        <li v-for="volume in volumes" :key="volume.name" class="relative">
                            <!-- Icon mở/đóng danh sách chương -->
                            <div class="flex items-center w-full">
                                <button @click="toggleChapters(volume.name)"
                                    class="flex items-center text-left font-medium hover:underline mr-2">
                                    <font-awesome-icon
                                        :icon="activeChapters[volume.name] ? 'fas fa-square-minus' : 'fas fa-square-plus'"
                                        size='xl' />
                                </button>

                                <!-- Tên tập, nhấn vào sẽ mở dropdown -->
                                <button @click="toggleDropdown(volume.name)"
                                    class="text-left w-full font-medium hover:underline">
                                    {{ volume.name }}
                                </button>
                            </div>

                            <!-- Dropdown menu cho tập -->
                            <div v-if="activeDropdown === volume.name"
                                class="z-10 absolute mt-2 bg-white border border-gray-300 rounded-md shadow-lg">
                                <ul class="py-1 text-sm text-gray-700">
                                    <li>
                                        <button @click="addChapter(volume.name)"
                                            class="block w-full px-4 py-2 hover:bg-gray-100">
                                            Thêm chương
                                        </button>
                                    </li>
                                    <li>
                                        <button @click="editVolume(volume.name)"
                                            class="block w-full px-4 py-2 hover:bg-gray-100">
                                            Sửa tập
                                        </button>
                                    </li>
                                    <li>
                                        <button @click="sortChapters(volume.name)"
                                            class="block w-full px-4 py-2 hover:bg-gray-100">
                                            Sắp xếp chương
                                        </button>
                                    </li>
                                </ul>
                            </div>

                            <!-- Danh sách chương con dưới dạng ma trận -->
                            <div v-if="activeChapters[volume.name]" class="mt-2 relative">
                                <div class="grid grid-cols-1 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                                    <div v-for="chapter in volume.chapters" :key="chapter.id"
                                        class="text-left relative">
                                        <button @click="toggleChapterDropdown(chapter.id)"
                                            class="text-sm text-blue-600 hover:underline">
                                            {{ chapter.name }}
                                        </button>

                                        <!-- Dropdown menu cho chương -->
                                        <div v-if="activeDropdown === chapter.id"
                                            class="z-10 absolute left-0 mt-2 bg-white border border-gray-300 rounded-md shadow-lg">
                                            <ul class="py-1 text-sm text-gray-700">
                                                <li>
                                                    <button @click="viewChapter(chapter.id)"
                                                        class="block w-full px-4 py-2 hover:bg-gray-100">
                                                        Xem chương
                                                    </button>
                                                </li>
                                                <li>
                                                    <button @click="editChapter(chapter.id)"
                                                        class="block w-full px-4 py-2 hover:bg-gray-100">
                                                        Chỉnh sửa chương
                                                    </button>
                                                </li>
                                                <li>
                                                    <button @click="deleteChapter(chapter.id)"
                                                        class="block w-full px-4 py-2 text-red-600 hover:bg-gray-100">
                                                        Xóa chương
                                                    </button>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>

    <div class="flex justify-center items-center max-w-7xl mx-auto">
    <EditContentChapter v-if="showEditChapter" class="my-10" />
    <AddChapter v-if="showAddChapter" class="my-10" />
    <OrderSortVolume v-if="showOrderSortVolume" class="my-10" />
</div>
</template>

<script setup lang="ts">
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { ref } from "vue";
import EditContentChapter from "./EditContentChapter.vue";
import AddChapter from "./AddChapter.vue";
import OrderSortVolume from "./OrderSortVolume.vue";
const activeChapters = ref<Record<string, boolean>>({}); // Trạng thái mở/đóng các chương
const activeDropdown = ref<string | number | null>(null); // Trạng thái mở/đóng dropdown menu của tập/chương

// Dữ liệu mẫu
const volumes = ref([
    {
        name: "Tập 01: Tài liệu về chuyện thám hiểm kỳ dị của họ",
        chapters: [
            { id: 1, name: "Chương 1: Sản tẩm Kunekune - 1" },
            { id: 2, name: "Chương 2: Sản tẩm Kunekune - 2" },
            { id: 3, name: "Chương 3: Những bí ẩn về ma quái - 1" },
            { id: 4, name: "Chương 4: Những bí ẩn về ma quái - 2" },
            { id: 5, name: "Chương 5: Đoạn đường hiểm trở - 1" },
            { id: 6, name: "Chương 6: Đoạn đường hiểm trở - 2" },
            { id: 7, name: "Chương 7: Thử nghiệm kỳ lạ - 1" },
            { id: 8, name: "Chương 8: Thử nghiệm kỳ lạ - 2" },
            { id: 9, name: "Chương 9: Cuộc truy lùng bí ẩn - 1" },
            { id: 10, name: "Chương 10: Cuộc truy lùng bí ẩn - 2" },
            { id: 11, name: "Chương 11: Mảnh vỡ từ quá khứ - 1" },
            { id: 12, name: "Chương 12: Mảnh vỡ từ quá khứ - 2" },
            { id: 13, name: "Chương 13: Đường vào thế giới lạ - 1" },
            { id: 14, name: "Chương 14: Đường vào thế giới lạ - 2" },
            { id: 15, name: "Chương 15: Cảnh báo từ tương lai - 1" },
            { id: 16, name: "Chương 16: Cảnh báo từ tương lai - 2" },
            { id: 17, name: "Chương 17: Đoạt lại ký ức - 1" },
            { id: 18, name: "Chương 18: Đoạt lại ký ức - 2" },
            { id: 19, name: "Chương 19: Câu chuyện chưa kể - 1" },
            { id: 20, name: "Chương 20: Câu chuyện chưa kể - 2" },
            { id: 21, name: "Chương 21: Khám phá vực thẳm - 1" },
            { id: 22, name: "Chương 22: Khám phá vực thẳm - 2" },
            { id: 23, name: "Chương 23: Cuộc hành trình bí ẩn - 1" },
            { id: 24, name: "Chương 24: Cuộc hành trình bí ẩn - 2" },
            { id: 25, name: "Chương 25: Bí mật từ bên kia - 1" },
            { id: 26, name: "Chương 26: Bí mật từ bên kia - 2" },
            { id: 27, name: "Chương 27: Con đường chưa thấy - 1" },
            { id: 28, name: "Chương 28: Con đường chưa thấy - 2" },
            { id: 29, name: "Chương 29: Dự báo từ hiện tại - 1" },
            { id: 30, name: "Chương 30: Dự báo từ hiện tại - 2" },
        ]
    },
]);
// State to control the visibility of the dropdown menu
const isDropdownVisible = ref(false);

// Method to toggle dropdown visibility
const toggleDropdownNovel = () => {
    isDropdownVisible.value = !isDropdownVisible.value;
};

// Method to handle option click
const handleOptionClick = (option: string) => {
    console.log(option); // Handle each option here
    isDropdownVisible.value = false; // Close dropdown after selecting an option
};
// Hàm xử lý sự kiện
const toggleChapters = (volumeName: string) => {
    activeChapters.value[volumeName] = !activeChapters.value[volumeName];
};

const toggleDropdown = (volumeName: string) => {
    activeDropdown.value = activeDropdown.value === volumeName ? null : volumeName;
};

const toggleChapterDropdown = (chapterId: number) => {
    activeDropdown.value = activeDropdown.value === chapterId ? null : chapterId;
};
const viewChapter = (id: number) => {
    alert(`Xem chương có ID: ${id}`);
};


const deleteChapter = (id: number) => {
    alert(`Xóa chương có ID: ${id}`);
};


const editVolume = (volumeName: string) => {
    alert(`Sửa tập: ${volumeName}`);
};
// Trạng thái hiển thị các component
const showEditChapter = ref(false);
const showAddChapter = ref(false);
const selectedChapterId = ref<number | null>(null);
const showOrderSortVolume = ref(false);
const sortChapters = (novelId: string) => {
    showOrderSortVolume.value = true;
    showEditChapter.value = false; // Ẩn component chỉnh sửa
    showAddChapter.value = false; // Ẩn component thêm chương
};
// Hàm xử lý khi nhấn vào "Chỉnh sửa chương"
const editChapter = (id: number) => {
    selectedChapterId.value = id; // Lưu ID chương được chọn
    showEditChapter.value = true; // Hiển thị component chỉnh sửa
    showAddChapter.value = false; // Ẩn component thêm chương
};

// Hàm xử lý khi nhấn vào "Thêm chương"
const addChapter = (volumeName: string) => {
    showAddChapter.value = true; // Hiển thị component thêm chương
    showEditChapter.value = false; // Ẩn component chỉnh sửa
};

</script>