<script lang="ts" setup>
import {inject, onMounted, ref} from "vue";
import {deleteNovel, getNovel} from "@/api/novel";
import EditContentChapter from "@/components/admin/EditNovelChapter.vue";
import AddChapter from "@/components/admin/AddNovelChapter.vue";
import AddNovelVolume from "@/components/admin/AddNovelVolume.vue";
import EditNovelVolume from "@/components/admin/EditNovelVolume.vue";
import {useRoute} from "vue-router";
import {deleteVolume, getVolumesByNovelId} from "@/api/novelVolume";
import {deleteChapter, getChaptersByVolumeId} from "@/api/novelChapter";
import router from "@/router";
import ConfirmDeleteModal from "@/components/common/ConfirmDeleteModal.vue";
import NovelEdit from "@/components/admin/NovelEdit.vue";
import OrderSortChapter from "@/components/admin/OrderSortChapter.vue";
import OrderSortVolume from "@/components/admin/OrderSortVolume.vue";
import {notification} from "ant-design-vue";
interface Chapter {
  id: string;
  chapterTitle: string;
  chapterNumber: number;
}

interface Volume {
  volumeName: string;
  id: string;
  chapters: Chapter[];
}

interface Novel {
  id: string;
  title: string;
  authorName: string;
  description: string;
  bookStatus: string;
  categories: Array<{ name: string }>;
  coverPicture: string;
  image: {
    id: string;
    name: string;
    type: string;
    path: string;
  };
}

const activeChapters = ref<Record<string, boolean>>({});
const activeDropdown = ref<string | number | null>(null);
const volumes = ref<Volume[]>([]);
const isDropdownVisible = ref(false);
const novel = ref<Novel | Record<string, any>>({});
const showEditNovel = ref(false);
const showEditChapter = ref(false);
const showAddChapter = ref(false);
const showOrderSortChapter = ref(false);
const showOrderSortVolume = ref(false);
const showEditVolume = ref(false);
const showAddVolume = ref(false);
const selectedVolumeData = ref<Volume | Record<string, any>>({});
const selectedChapterData = ref<Chapter | Record<string, any>>({});
const showConfirmModal = ref(false);
const itemToDelete = ref<{ id: string, type: string } | null>(null);

const route = useRoute();
const id = Array.isArray(route.params.id) ? route.params.id[0] : route.params.id;

const fetchChaptersForVolume = async (volumeId: string) => {
  try {
    const response = await getChaptersByVolumeId(volumeId);
    console.log(response);
    const chapters = response.map((chapter: any) => ({
      id: chapter.id,
      volumeId: chapter.volumeId,
      chapterTitle: chapter.chapterTitle,
      chapterNumber: chapter.chapterNumber,
      content: chapter.content,
      created: chapter.created,
      createdDate: chapter.createdDate,
      status: chapter.status,
    }));
    const volume = volumes.value.find(v => v.id === volumeId);
    if (volume) {
      volume.chapters = chapters.length ? chapters : [];
    }
  } catch (error) {
    console.error("Error fetching chapters:", error);
    const volume = volumes.value.find(v => v.id === volumeId);
    if (volume) {
      volume.chapters = [];
    }
  }
};

const refreshNovelData = async () => {
  try {
    const novelData = await getNovel(id);
    console.log(novelData);
    novel.value = {
      id: novelData.id,
      title: novelData.title,
      authorName: novelData.authorName,
      description: novelData.description,
      bookStatus: novelData.status,
      categories: novelData.categories,
      image: novelData.image,
    };

    const volumeData = await getVolumesByNovelId(id);
    volumes.value = volumeData;

    const activeChaptersState = {...activeChapters.value};
    activeChapters.value = {};
    volumes.value.forEach(volume => {
      activeChapters.value[volume.volumeName] = activeChaptersState[volume.volumeName] || false;
      if (activeChapters.value[volume.volumeName]) {
        fetchChaptersForVolume(volume.id);
      }
    });
  } catch (error) {
    console.error("Error refreshing novel data:", error);
  }
};

const toggleChapters = async (volumeName: string) => {
  const volume = volumes.value.find(v => v.volumeName === volumeName);
  if (volume) {
    if (!volume.chapters) {
      await fetchChaptersForVolume(volume.id);
    }
    activeChapters.value[volumeName] = !activeChapters.value[volumeName];
  } else {
    activeChapters.value[volumeName] = true;
  }
};

const toggleDropdownNovel = () => {
  isDropdownVisible.value = !isDropdownVisible.value;
  activeDropdown.value = null;
};

const toggleDropdown = (volumeName: string) => {
  activeDropdown.value = activeDropdown.value === volumeName ? null : volumeName;
  isDropdownVisible.value = false;
};

const toggleChapterDropdown = (chapterId: string) => {
  activeDropdown.value = activeDropdown.value === chapterId ? null : chapterId;
  isDropdownVisible.value = false;
};

const viewChapter = (chapterId: string, novelId: string) => {
  router.push({ name: 'chapter', params: { novel: novelId, chapter: chapterId } });
};

const viewNovel = (novelId: string) => {
  router.push({ name: 'noveldetail', params: { id: novelId } });
};
const showModal = (modalName: string) => {
  showEditNovel.value = false;
  showEditVolume.value = false;
  showAddVolume.value = false;
  showEditChapter.value = false;
  showAddChapter.value = false;
  showOrderSortChapter.value = false;
  showOrderSortVolume.value = false;
  if (modalName === 'editNovel') showEditNovel.value = true;
  if (modalName === 'editVolume') showEditVolume.value = true;
  if (modalName === 'addVolume') showAddVolume.value = true;
  if (modalName === 'editChapter') showEditChapter.value = true;
  if (modalName === 'addChapter') showAddChapter.value = true;
  if (modalName === 'orderSortChapter') showOrderSortChapter.value = true;
  if (modalName === 'orderSortVolume') showOrderSortVolume.value = true;
};
const sortVolumes = () => {
  showModal('orderSortVolume');
}
const sortChapters = (volumeId: string) => {
  if (volumeId) {
    const volume = volumes.value.find(v => v.id === volumeId);
    if (volume) {
      selectedVolumeData.value = volume;
      showModal('orderSortChapter');
    } else {
      selectedVolumeData.value = {};
    }
  }
};

const editChapter = (chapter: Chapter) => {
  selectedChapterData.value = chapter;
  showModal('editChapter');
};

const addChapter = (volumeId: string | null) => {
  if (volumeId) {
    const volume = volumes.value.find(v => v.id === volumeId);
    if (volume) {
      selectedVolumeData.value = volume;
      showModal('addChapter');
    } else {
      selectedVolumeData.value = {};
    }
  }
};

const addVolume = () => {
  showModal('addVolume');
};

const editVolume = (volume: Volume) => {
  selectedVolumeData.value = volume;
  showModal('editVolume');
};

const editNovel = () => {
  showModal('editNovel');
};

const handleChapterUpdated = async () => {
  if (selectedVolumeData.value && selectedVolumeData.value.id) {
    await fetchChaptersForVolume(selectedVolumeData.value.id);
  }
  await refreshNovelData();
};

const handleVolumeUpdated = async () => {
  await refreshNovelData();
};

const handleVolumeCreated = async () => {
  await refreshNovelData();
};

const handleDelete = (id: string, type: string) => {
  itemToDelete.value = {id, type};
  showConfirmModal.value = true;
};
const handleNovelUpdated = async () => {
  await refreshNovelData();
};
const showNotification = (type: string, message: string) => {
  notification[type]({
    message: type === 'success' ? 'Success' : 'Error',
    description: message,
    duration: 3
  });
};

const confirmDelete = async () => {
  if (itemToDelete.value) {
    try {
      if (itemToDelete.value.type === "novel") {
        await deleteNovel(itemToDelete.value.id);
        router.back();
      } else if (itemToDelete.value.type === "volume") {
        await deleteVolume(itemToDelete.value.id);
      } else if (itemToDelete.value.type === "chapter") {
        await deleteChapter(itemToDelete.value.id);
        if (selectedVolumeData.value && selectedVolumeData.value.id) {
          await fetchChaptersForVolume(selectedVolumeData.value.id);
        }
      }
      showNotification('success', `${itemToDelete.value.type} deleted successfully.`);
      await refreshNovelData();
    } catch (error: any) {
      console.error('Failed to delete item:', error);
      if (error.response) {
        showNotification('danger', error.response.data.message || 'Item delete failed. Please try again.');
      } else if (error.request) {
        showNotification('danger', 'No response from server. Please try again.');
      } else {
        showNotification('danger', 'An unexpected error occurred. Please try again.');
      }
    }
  }
  showConfirmModal.value = false;
};

const cancelDelete = () => {
  showConfirmModal.value = false;
};

onMounted(() => {
  refreshNovelData();
});
</script>

<template>
  <ConfirmDeleteModal
      :show="showConfirmModal"
      confirmText="Delete"
      message="This action cannot be undone."
      title="Are you sure you want to delete?"
      @cancel="cancelDelete"
      @confirm="confirmDelete"
  />
  <div class="flex justify-center items-center max-w-7xl mx-auto">
    <div class="bg-[#F8F8F7] p-8 rounded-lg shadow-md w-full">
      <p class="text-lg font-bold">Novel Management</p>
      <div class="space-y-4 mt-10">
        <div class="md:col-span-1 relative">
          <p class="text-lg font-bold cursor-pointer" @click="toggleDropdownNovel">{{ novel.title }}</p>
          <div v-if="isDropdownVisible"
               class="absolute z-10 bg-[#F8F8F7] border border-gray-300 rounded-md mt-2 w-36 shadow-md">
            <button class="block w-full p-2 cursor-pointer hover:bg-gray-100 text-sm" @click="viewNovel(novel.id)">Xem
              tiểu thuyết
            </button>
            <button class="block w-full p-2 cursor-pointer hover:bg-gray-100 text-sm" @click="addVolume">
              Thêm tập
            </button>
            <button class="block w-full p-2 cursor-pointer hover:bg-gray-100 text-sm" @click="sortVolumes">Sắp xếp tập</button>
            <button class="block w-full p-2 cursor-pointer hover:bg-gray-100 text-sm" @click="editNovel">Sửa tiểu
              thuyết
            </button>
            <button class="block w-full p-2 cursor-pointer hover:bg-gray-100 text-sm"
                    @click="() => { handleDelete(novel.id, 'novel'); }">Xóa tiểu thuyết
            </button>
          </div>
        </div>
        <div>
          <ul class="space-y-4 text-gray-700">
            <li v-for="volume in volumes" :key="volume.volumeName" class="relative">
              <div class="flex items-center w-full">
                <button class="flex items-center text-left font-medium hover:underline mr-2"
                        @click="toggleChapters(volume.volumeName)">
                  <font-awesome-icon
                      :icon="activeChapters[volume.volumeName] ? 'fas fa-square-minus' : 'fas fa-square-plus'"
                      size='xl'/>
                </button>
                <button class="text-left w-full font-medium hover:underline" @click="toggleDropdown(volume.volumeName)">
                  {{ volume.volumeName }}
                </button>
              </div>
              <div v-if="activeDropdown === volume.volumeName"
                   class="z-10 absolute mt-2 bg-[#F8F8F7] border border-gray-300 rounded-md shadow-lg">
                <ul class="py-1 text-sm text-gray-700">
                  <li>
                    <button class="block w-full px-4 py-2 hover:bg-gray-100" @click="() => { addChapter(volume.id) }">
                      Thêm chương
                    </button>
                  </li>
                  <li><button @click="() => { sortChapters(volume.id)}" class="block w-full px-4 py-2 hover:bg-gray-100">Sắp xếp chương</button></li>
                  <li>
                    <button class="block w-full px-4 py-2 hover:bg-gray-100" @click="() => { editVolume(volume) }">Sửa
                      tập
                    </button>
                  </li>
                  <li>
                    <button class="block w-full px-4 py-2 text-red-600 hover:bg-gray-100"
                            @click="() => { handleDelete(volume.id, 'volume')}">Xóa tập
                    </button>
                  </li>
                </ul>
              </div>
              <div v-if="activeChapters[volume.volumeName]" class="mt-2 relative">
                <div v-if="!volume.chapters || volume.chapters.length === 0" class="text-gray-500 text-sm">
                  Không có chapter
                </div>
                <div class="grid grid-cols-1 sm:grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                  <div v-for="chapter in volume.chapters" :key="chapter.id" class="text-left relative">
                    <button class="text-sm text-blue-600 hover:underline" @click="toggleChapterDropdown(chapter.id)">
                      {{ chapter.chapterTitle }}
                    </button>
                    <div v-if="activeDropdown === chapter.id"
                         class="z-10 absolute left-0 mt-2 bg-[#F8F8F7] border border-gray-300 rounded-md shadow-lg">
                      <ul class="py-1 text-sm text-gray-700">
                        <li>
                          <button class="block w-full px-4 py-2 hover:bg-gray-100"
                                  @click="() => { viewChapter(chapter.id, novel.id)}">Xem chương
                          </button>
                        </li>
                        <li>
                          <button class="block w-full px-4 py-2 hover:bg-gray-100"
                                  @click="() => { editChapter(chapter)}">Chỉnh sửa chương
                          </button>
                        </li>
                        <li>
                          <button class="block w-full px-4 py-2 text-red-600 hover:bg-gray-100"
                                  @click="() => { handleDelete(chapter.id, 'chapter')}">Xóa chương
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
    <EditContentChapter v-if="showEditChapter" :chapterData="selectedChapterData"
                        class="my-10" @chapter-updated="handleChapterUpdated"/>
    <AddChapter v-if="showAddChapter" :volumeId="selectedVolumeData.id" class="my-10"
                @chapter-added="handleChapterUpdated"/>
    <AddNovelVolume v-if="showAddVolume" :novelId="novel.id" class="my-10" @volume-added="handleVolumeCreated"/>
    <EditNovelVolume v-if="showEditVolume" :volumeData="selectedVolumeData" class="my-10"
                     @volume-updated="handleVolumeUpdated"/>
    <NovelEdit v-if="showEditNovel" :novel="novel" class="my-10" @novel-updated="handleNovelUpdated"/>
    <OrderSortChapter v-if="showOrderSortChapter" :volumeId="selectedVolumeData.id" class="my-10" @chapter-order-updated="handleChapterUpdated"/>
    <OrderSortVolume v-if="showOrderSortVolume" :novelId="novel.id" class="my-10" @volume-order-updated="handleVolumeUpdated"/>
  </div>
</template>