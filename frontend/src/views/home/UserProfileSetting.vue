<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { getUserProfile, updateUserProfile } from "@/api/user";
import { changePassword } from "@/api/auth";
import store from "@/store";
import Banner from "@/components/home/Banner.vue";
import Tiptap from "@/components/common/Tiptap.vue";

const profile = ref({
  id: '',
  image: '',
  username: '',
  gender: '',
  dateOfBirth: '',
  bio: '',
});

const email = store.getters.getEmail;

const passwords = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: '',
});

const activeTab = ref('profile');
const isDarkMode = ref(localStorage.getItem('theme') === 'dark');

const fetchUserProfile = async () => {
  try {
    const userProfile = await getUserProfile(store.getters.getUserId);
    profile.value = userProfile;
    profile.value.image = userProfile.image?.path || 'default-avatar.png'; // Use default image if none exists
  } catch (error) {
    console.error(error);
  }
}
const selectedImage = ref<File | null>(null);

const handleProfileSubmit = async () => {
  const profileData = {
    id: profile.value.id,
    username: profile.value.username,
    userId: store.getters.getUserId,
    dateOfBirth: profile.value.dateOfBirth,
    bio: profile.value.bio,
    gender: profile.value.gender,
    imageUrl: selectedImage.value ? '' : profile.value.image // Send empty string if new image is selected
  };
  const formData = new FormData();
  formData.append("profile", new Blob([JSON.stringify(profileData)], { type: "application/json" }));
  if (selectedImage.value) {
    formData.append("image", selectedImage.value);
  }
  try {
    await updateUserProfile(profile.value.id, formData);
    alert('Profile Updated Successfully!');
  } catch (error) {
    console.error(error);
  }
}
const handleChangePassword = async () => {
  if (passwords.value.newPassword !== passwords.value.confirmPassword) {
    alert('New password and confirm password do not match!');
    return;
  }

  try {
    await changePassword(JSON.stringify({
      oldPassword: passwords.value.oldPassword,
      newPassword: passwords.value.newPassword,
      token: store.getters.getToken,
    }));

    // Reset password fields
    passwords.value = {
      oldPassword: '',
      newPassword: '',
      confirmPassword: ''
    };

    alert('Password Changed Successfully!');
  } catch (error) {
    console.error(error);
  }
}

const toggleTheme = () => {
  isDarkMode.value = !isDarkMode.value;
  const theme = isDarkMode.value ? 'dark' : 'light';
  localStorage.setItem('theme', theme);
  document.documentElement.classList.toggle('dark', isDarkMode.value);
}
const handleImageChange = (event: Event) => {
  const file = (event.target as HTMLInputElement).files?.[0];
  if (file) {
    selectedImage.value = file;
    profile.value.image = URL.createObjectURL(file);
  }
};
const formattedDateOfBirth = computed({
  get() {
    return profile.value.dateOfBirth ? profile.value.dateOfBirth.split('T')[0] : '';
  },
  set(value) {
    profile.value.dateOfBirth = value;
  }
});

onMounted(() => {
  fetchUserProfile();
  // Initial theme setup
  document.documentElement.classList.toggle('dark', isDarkMode.value);
});
</script>

<template>
  <Banner class="max-w-7xl mx-auto mt-5"/>
  <div class="container mx-auto px-4 py-8 max-w-5xl min-h-screen">
    <div class="bg-white dark:bg-gray-800 shadow-2xl rounded-2xl overflow-hidden">
      <!-- TabSwitch Navigation -->
      <!-- TabSwitch Navigation -->
      <div class="flex bg-gray-100 dark:bg-gray-700 rounded-full p-1 mx-4 mt-4 mb-2">
        <button
            @click="activeTab = 'profile'"
            class="flex-1 py-2 px-4 rounded-full text-center transition-all duration-300"
            :class="{
      'bg-white dark:bg-gray-800 shadow-md text-blue-600 dark:text-blue-400': activeTab === 'profile',
      'text-gray-500 hover:bg-gray-200 dark:hover:bg-gray-600': activeTab !== 'profile'
    }"
        >
          <font-awesome-icon :icon="['fas', 'user']" class="inline-block mr-2"/> Profile
        </button>
        <button
            @click="activeTab = 'security'"
            class="flex-1 py-2 px-4 rounded-full text-center transition-all duration-300"
            :class="{
      'bg-white dark:bg-gray-800 shadow-md text-blue-600 dark:text-blue-400': activeTab === 'security',
      'text-gray-500 hover:bg-gray-200 dark:hover:bg-gray-600': activeTab !== 'security'
    }"
        >
          <font-awesome-icon :icon="['fas', 'key']" class="inline-block mr-2"/> Security
        </button>
        <button
            @click="activeTab = 'theme'"
            class="flex-1 py-2 px-4 rounded-full text-center transition-all duration-300"
            :class="{
      'bg-white dark:bg-gray-800 shadow-md text-blue-600 dark:text-blue-400': activeTab === 'theme',
      'text-gray-500 hover:bg-gray-200 dark:hover:bg-gray-600': activeTab !== 'theme'
    }"
        >
          <font-awesome-icon :icon="['fas', 'sun']" v-if="!isDarkMode" class="inline-block mr-2"/>
          <font-awesome-icon :icon="['fas', 'moon']" v-else class="inline-block mr-2"/> Theme
        </button>
      </div>
      <!-- Profile TabSwitch -->
      <div v-if="activeTab === 'profile'" class="p-6 space-y-6">
        <div class="flex items-center space-x-6">
          <div class="relative">
            <img
                :src="profile.image || 'default-avatar.png'"
                alt=""
                class="w-24 h-24 rounded-full object-cover border-4 border-blue-500"
            />
            <input
                type="file"
                @change="handleImageChange"
                class="absolute inset-0 opacity-0 cursor-pointer"
            />
          </div>
          <div>
            <h2 class="text-2xl font-bold dark:text-white">{{ profile.username }}</h2>
            <p class="text-gray-500">{{ email }}</p>
          </div>
        </div>

        <form @submit.prevent="handleProfileSubmit" class="grid md:grid-cols-2 gap-6">
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium dark:text-gray-300">Username</label>
              <input
                  v-model="profile.username"
                  type="text"
                  class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-[#889b6c] focus:border-[#889b6c] sm:text-sm"
              />
            </div>
            <div>
              <label class="block text-sm font-medium dark:text-gray-300">Gender</label>
              <select
                  v-model="profile.gender"
                  class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-[#889b6c] focus:border-[#889b6c] sm:text-sm"
              >
                <option value="male">Male</option>
                <option value="female">Female</option>
                <option value="other">Other</option>
              </select>
            </div>
          </div>
          <div class="space-y-4">
            <div>
              <label class="block text-sm font-medium dark:text-gray-300">Date of Birth</label>
              <input
                  v-model="formattedDateOfBirth"
                  type="date"
                  class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-[#889b6c] focus:border-[#889b6c] sm:text-sm"
              />
            </div>
          </div>
          <div class="md:col-span-2 space-y-4">
            <div>
              <label class="block text-sm font-medium dark:text-gray-300">Bio</label>
              <Tiptap :content="profile.bio" @update:content="profile.bio = $event"/>
            </div>
          </div>
          <div class="md:col-span-2 flex justify-end">
            <button type="submit" class="px-4 py-2 text-sm font-medium text-blue-500 transition-all duration-300 border-[1px] border-blue-500 rounded hover:border-blue-700 hover:scale-105">
              Update Profile
            </button>
          </div>
        </form>
      </div>

      <!-- Security TabSwitch -->
      <div v-else-if="activeTab === 'security'" class="p-6 space-y-6">
        <form @submit.prevent="handleChangePassword" class="space-y-4">
          <div>
            <label class="block text-sm font-medium dark:text-gray-300">Current Password</label>
            <input
                v-model="passwords.oldPassword"
                type="password"
                class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-[#889b6c] focus:border-[#889b6c] sm:text-sm"
            />
          </div>
          <div>
            <label class="block text-sm font-medium dark:text-gray-300">New Password</label>
            <input
                v-model="passwords.newPassword"
                type="password"
                class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-[#889b6c] focus:border-[#889b6c] sm:text-sm"
            />
          </div>
          <div>
            <label class="block text-sm font-medium dark:text-gray-300">Confirm New Password</label>
            <input
                v-model="passwords.confirmPassword"
                type="password"
                class="mt-1 block w-full px-4 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-[#889b6c] focus:border-[#889b6c] sm:text-sm"
            />
          </div>
          <div class="flex justify-end">
            <button
                type="submit"
                class="px-6 py-2 bg-blue-500 text-white rounded-md hover:bg-blue-600 transition-colors"
            >
              Change Password
            </button>
          </div>
        </form>
      </div>

      <!-- Theme TabSwitch -->
      <div v-else-if="activeTab === 'theme'" class="p-6 space-y-6">
        <div class="flex justify-between items-center">
          <div>
            <h3 class="text-lg font-medium dark:text-white">Theme</h3>
            <p class="text-sm text-gray-500">Switch between light and dark modes</p>
          </div>
          <button
              @click="toggleTheme"
              class="p-2 rounded-full w-10 h-10 bg-gray-200 dark:bg-gray-700 hover:bg-gray-300 dark:hover:bg-gray-600 transition-colors"
          >
            <font-awesome-icon :icon="['fas', 'sun']" v-if="!isDarkMode" class="text-yellow-500"/>
            <font-awesome-icon :icon="['fas', 'moon']"v-else class="text-indigo-500"/>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>