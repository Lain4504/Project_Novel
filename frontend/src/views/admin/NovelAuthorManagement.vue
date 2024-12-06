<script setup lang="ts">
import {ref} from "vue";
import EditContentChapter from "@/components/admin/EditNovelChapter.vue";
import AddChapter from "@/components/admin/AddNovelChapter.vue";
import OrderSortChapter from "@/components/admin/OrderSortChapter.vue";
import AddNovelVolume from "@/components/admin/AddNovelVolume.vue";
import EditNovelVolume from "@/components/admin/EditNovelVolume.vue";

const activeChapters = ref<Record<string, boolean>>({});
const activeDropdown = ref<string | number | null>(null);

const volumes = ref([
  {
    name: "Tập 01: Tài liệu về chuyện thám hiểm kỳ dị của họ",
    chapters: [
      {id: 1, name: "Chương 1: Sản tẩm Kunekune - 1"},
      {id: 2, name: "Chương 2: Sản tẩm Kunekune - 2"},
    ]
  },
]);
const novel = ref({
  name: "Urasekai Picnic",
});

const isDropdownVisible = ref(false);

const toggleDropdownNovel = () => {
  isDropdownVisible.value = !isDropdownVisible.value;
};

const handleOptionClick = (option: string) => {
  console.log(option);
  isDropdownVisible.value = false;
};

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

const showEditNovel = ref(false);
const showEditChapter = ref(false);
const showAddChapter = ref(false);
const showOrderSortChapter = ref(false);
const showEditVolume = ref(false);
const showAddVolume = ref(false);

const showModal = (modalName: string) => {
  showEditNovel.value = modalName === 'editNovel';
  showEditVolume.value = modalName === 'editVolume';
  showAddVolume.value = modalName === 'addVolume';
  showEditChapter.value = modalName === 'editChapter';
  showAddChapter.value = modalName === 'addChapter';
  showOrderSortChapter.value = modalName === 'orderSortChapter';
};

const sortChapters = (novelId: string) => {
  showModal('orderSortChapter');
};

const editChapter = (id: number) => {
  showModal('editChapter');
};

const addChapter = (volumeName: string) => {
  showModal('addChapter');
};

const addVolume = (novelName: string) => {
  showModal('addVolume');
};

const editVolume = (novelName: string) => {
  showModal('editVolume');
};

const editNovel = () => {
  showModal('editNovel');
};
</script>

<template>
  <div class="flex justify-center items-center max-w-7xl mx-auto">
    <div class="bg-[#F8F8F7] p-8 rounded-lg shadow-md w-full">
      <p class="text-lg font-bold">Novel Management</p>
      <div class="space-y-4 mt-10">
        <div class="md:col-span-1 relative">
          <p class="text-lg font-bold cursor-pointer" @click="toggleDropdownNovel">Urasekai Picnic</p>
          <div v-if="isDropdownVisible" class="absolute z-10 bg-[#F8F8F7] border border-gray-300 rounded-md mt-2 w-36 shadow-md">
            <button class="block w-full p-2 cursor-pointer hover:bg-gray-100 text-sm" @click="addVolume(novel.name)">Thêm tập</button>
            <button class="block w-full p-2 cursor-pointer hover:bg-gray-100 text-sm" @click="handleOptionClick('Xóa novel')">Xóa novel</button>
          </div>
        </div>
        <div>
          <ul class="space-y-4 text-gray-700">
            <li v-for="volume in volumes" :key="volume.name" class="relative">
              <div class="flex items-center w-full">
                <button @click="toggleChapters(volume.name)" class="flex items-center text-left font-medium hover:underline mr-2">
                  <font-awesome-icon :icon="activeChapters[volume.name] ? 'fas fa-square-minus' : 'fas fa-square-plus'" size='xl'/>
                </button>
                <button @click="toggleDropdown(volume.name)" class="text-left w-full font-medium hover:underline">{{ volume.name }}</button>
              </div>
              <div v-if="activeDropdown === volume.name" class="z-10 absolute mt-2 bg-[#F8F8F7] border border-gray-300 rounded-md shadow-lg">
                <ul class="py-1 text-sm text-gray-700">
                  <li><button @click="addChapter(volume.name)" class="block w-full px-4 py-2 hover:bg-gray-100">Thêm chương</button></li>
                  <li><button @click="editVolume(volume.name)" class="block w-full px-4 py-2 hover:bg-gray-100">Sửa tập</button></li>
                  <li><button @click="sortChapters(volume.name)" class="block w-full px-4 py-2 hover:bg-gray-100">Sắp xếp chương</button></li>
                </ul>
              </div>
              <div v-if="activeChapters[volume.name]" class="mt-2 relative">
                <div class="grid grid-cols-1 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                  <div v-for="chapter in volume.chapters" :key="chapter.id" class="text-left relative">
                    <button @click="toggleChapterDropdown(chapter.id)" class="text-sm text-blue-600 hover:underline">{{ chapter.name }}</button>
                    <div v-if="activeDropdown === chapter.id" class="z-10 absolute left-0 mt-2 bg-[#F8F8F7] border border-gray-300 rounded-md shadow-lg">
                      <ul class="py-1 text-sm text-gray-700">
                        <li><button @click="viewChapter(chapter.id)" class="block w-full px-4 py-2 hover:bg-gray-100">Xem chương</button></li>
                        <li><button @click="editChapter(chapter.id)" class="block w-full px-4 py-2 hover:bg-gray-100">Chỉnh sửa chương</button></li>
                        <li><button @click="deleteChapter(chapter.id)" class="block w-full px-4 py-2 text-red-600 hover:bg-gray-100">Xóa chương</button></li>
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
    <EditContentChapter v-if="showEditChapter" class="my-10"/>
    <AddChapter v-if="showAddChapter" class="my-10"/>
    <OrderSortChapter v-if="showOrderSortChapter" class="my-10"/>
    <AddNovelVolume v-if="showAddVolume" class="my-10"/>
    <EditNovelVolume v-if="showEditVolume" class="my-10"/>
  </div>
</template>