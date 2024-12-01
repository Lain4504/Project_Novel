<template>
    <div class="max-w-7xl mx-auto p-6">
        <Ads class="my-4" />
        <Breadcrumb :breadcrumbs="[
            { label: 'Home', href: '/' },
            { label: 'Account', href: '/account' },
            { label: 'Setting', href: '/setting', isCurrent: true }
        ]" />
        <section class="p-5 my-2">
            <h1 class="text-2xl font-semibold mb-4">Profile Settings</h1>
            <!-- Profile Form -->
            <form @submit.prevent="handleProfileSubmit">
                <div class="space-y-6 p-4 rounded-lg">
                    <!-- Avatar và User Information Section -->
                    <div class="grid grid-cols-1 md:grid-cols-3 gap-4 items-center">
                        <!-- Avatar -->
                        <div class="flex justify-center md:col-span-1">
                            <div class="relative w-24 h-24">
                                <input type="file" @change="handleAvatarChange" id="avatar"
                                    class="absolute inset-0 w-full h-full opacity-0 cursor-pointer" />
                                <div class="relative w-full h-full overflow-hidden bg-gray-100 rounded-full">
                                    <img :src="profile.avatar || 'default-avatar.png'" alt="Avatar"
                                        class="w-full h-full object-cover" />
                                    <div
                                        class="absolute inset-0 bg-gray-800 bg-opacity-30 flex items-center justify-center opacity-0 hover:opacity-100 transition">
                                        <svg class="w-6 h-6 text-white" xmlns="http://www.w3.org/2000/svg" fill="none"
                                            viewBox="0 0 24 24" stroke="currentColor">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                d="M15.172 7l-6.414 6.414M7 7h.01M13 13l4 4m0 0l-4-4m4 4H7" />
                                        </svg>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- User Information -->
                        <div
                            class="border shadow-sm p-2 bg-gray-50 md:col-span-2 grid grid-cols-1 md:grid-cols-2 gap-4">
                            <!-- Username -->
<div class="relative z-0 w-full my-6 group">
    <input type="text" name="username" id="username"
        class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
        v-model="profile.username" placeholder=" " required />
    <label for="username"
        class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Username</label>
</div>

<!-- Email -->
<div class="relative z-0 w-full my-6 group">
    <input type="email" name="email" id="email"
        class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
        v-model="profile.email" placeholder=" " required />
    <label for="email"
        class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Email</label>
</div>


                         <div class="relative z-0 w-full my-6 group">
    <select name="gender" id="gender"
        class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
        v-model="profile.gender" required>
        <option value="" disabled>Select Gender</option>
        <option value="male">Male</option>
        <option value="female">Female</option>
        <option value="other">Other</option>
    </select>
    <label for="gender"
        class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Gender</label>
</div>

                           <!-- Date of Birth -->
<div class="relative z-0 w-full my-6 group">
    <input type="date" name="dob" id="dob"
        class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
        v-model="profile.dob" placeholder=" " required />
    <label for="dob"
        class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Date of Birth</label>
</div>

                        </div>
                    </div>

             <!-- Bio -->
<div class="relative z-0 w-full my-6 group">
    <textarea name="bio" id="bio"
        class="block py-2.5 px-0 w-full text-sm text-gray-900 bg-transparent border-0 border-b-2 border-gray-300 appearance-none dark:text-white dark:border-gray-600 dark:focus:border-blue-500 focus:outline-none focus:ring-0 focus:border-blue-600 peer"
        v-model="profile.bio" placeholder=" " required></textarea>
    <label for="bio"
        class="peer-focus:font-medium absolute text-sm text-gray-500 dark:text-gray-400 duration-300 transform -translate-y-6 scale-75 top-3 -z-10 origin-[0] peer-focus:start-0 peer-focus:text-blue-600 peer-focus:dark:text-blue-500 peer-placeholder-shown:scale-100 peer-placeholder-shown:translate-y-0 peer-focus:scale-75 peer-focus:-translate-y-6">Bio</label>
</div>



                </div>
                <div>
                    <h2 class="text-lg font-semibold mt-7 mb-4">Notification Settings</h2>
                    <!-- Sử dụng flex và chia thành hai cột -->
                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div>
                            <label class="inline-flex items-center">
                                <input type="checkbox" v-model="settings.newRead" class="form-checkbox" />
                                <span class="ml-2 text-sm">Mới đọc</span>
                            </label>
                        </div>
                        <div>
                            <label class="inline-flex items-center">
                                <input type="checkbox" v-model="settings.newMarked" class="form-checkbox" />
                                <span class="ml-2 text-sm">Sắp xếp truyện đánh dấu</span>
                            </label>
                        </div>
                        <div>
                            <label class="inline-flex items-center">
                                <input type="checkbox" v-model="settings.newSaved" class="form-checkbox" />
                                <span class="ml-2 text-sm">Mới lưu</span>
                            </label>
                        </div>
                        <div>
                            <label class="inline-flex items-center">
                                <input type="checkbox" v-model="settings.chapterNotifications" class="form-checkbox" />
                                <span class="ml-2 text-sm">Thông báo chương mới</span>
                            </label>
                        </div>
                        <div>
                            <label class="inline-flex items-center">
                                <input type="checkbox" v-model="settings.interactionNotifications"
                                    class="form-checkbox" />
                                <span class="ml-2 text-sm">Thông báo tương tác</span>
                            </label>
                        </div>
                    </div>
                </div>

                <div>
                    <h2 class="text-lg font-semibold mt-6 mb-4">Preferences</h2>
                    <div>
                        <label class="inline-flex items-center cursor-pointer">
                            <!-- Toggle switch -->
                            <input type="checkbox" v-model="settings.theme" class="sr-only peer"
                                :checked="settings.theme === 'dark'"
                                @change="settings.theme = $event.target.checked ? 'dark' : 'light'">

                            <div
                                class="relative w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[2px] after:start-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-blue-600">
                            </div>

                            <span class="ms-3 text-sm font-medium text-gray-900 dark:text-gray-300">Toggle
                                Theme</span>
                        </label>
                    </div>

                </div>
                <div class="flex justify-end mt-4">
                    <button
                        class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300 flex items-center justify-center">
                        Save Changes
                    </button>
                </div>
            </form>
            <!-- Change Password Form -->
            <div class="max-w-md">
                <form>
                    <div>
                        <h2 class="text-lg font-semibold mt-6 mb-4">Change Password</h2>
                        <div class="grid grid-cols-1 gap-4">
                            <div>
                                <label for="currentPassword" class="block text-sm font-medium text-gray-700">Current
                                    Password</label>
                                <input type="password" v-model="passwords.currentPassword" id="currentPassword"
                                    class="mt-1 block w-full p-2 border rounded-md" required />
                            </div>
                            <div>
                                <label for="newPassword" class="block text-sm font-medium text-gray-700">New
                                    Password</label>
                                <input type="password" v-model="passwords.newPassword" id="newPassword"
                                    class="mt-1 block w-full p-2 border rounded-md" required />
                            </div>
                            <div>
                                <label for="confirmPassword" class="block text-sm font-medium text-gray-700">Confirm New
                                    Password</label>
                                <input type="password" v-model="passwords.confirmPassword" id="confirmPassword"
                                    class="mt-1 block w-full p-2 border rounded-md" required />
                            </div>
                        </div>
                    </div>
                    <div class="flex justify-end mt-4">
                        <button
                            class="text-sm bg-transparent border-[1px] border-blue-500 text-blue-500 hover:border-blue-700 hover:scale-105 font-medium py-2 px-4 rounded transition-all duration-300 flex items-center justify-center">
                            Save Changes
                        </button>
                    </div>
                </form>
            </div>
            <div class="bg-white p-6 rounded-lg shadow-md mt-6">
                <h2 class="text-xl font-semibold mb-4">Login Activity</h2>
                <table class="min-w-full table-auto border-collapse">
                    <thead>
                        <tr class="bg-gray-100">
                            <th class="px-4 py-2 border text-left text-sm font-medium text-gray-700">Login Time</th>
                            <th class="px-4 py-2 border text-left text-sm font-medium text-gray-700">Location</th>
                            <th class="px-4 py-2 border text-left text-sm font-medium text-gray-700">Device</th>
                            <th class="px-4 py-2 border text-left text-sm font-medium text-gray-700">IP Address</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="px-4 py-2 border text-sm text-gray-900">2024-11-28 14:30</td>
                            <td class="px-4 py-2 border text-sm text-gray-900">New York, USA</td>
                            <td class="px-4 py-2 border text-sm text-gray-900">Chrome on Windows 10</td>
                            <td class="px-4 py-2 border text-sm text-gray-900">192.168.1.1</td>
                        </tr>
                        <tr>
                            <td class="px-4 py-2 border text-sm text-gray-900">2024-11-28 13:15</td>
                            <td class="px-4 py-2 border text-sm text-gray-900">London, UK</td>
                            <td class="px-4 py-2 border text-sm text-gray-900">Safari on macOS</td>
                            <td class="px-4 py-2 border text-sm text-gray-900">203.0.113.0</td>
                        </tr>
                        <tr>
                            <td class="px-4 py-2 border text-sm text-gray-900">2024-11-27 09:45</td>
                            <td class="px-4 py-2 border text-sm text-gray-900">Tokyo, Japan</td>
                            <td class="px-4 py-2 border text-sm text-gray-900">Firefox on Linux</td>
                            <td class="px-4 py-2 border text-sm text-gray-900">198.51.100.2</td>
                        </tr>
                    </tbody>
                </table>
            </div>

        </section>
    </div>
</template>

<script setup lang="ts">
import { ref, watch, computed } from 'vue';
import Breadcrumb from '@/components/common/Breadcrumb.vue';
import Ads from '@/components/home/Ads.vue';
// Data for profile, passwords, and settings
const profile = ref({
    username: '',
    email: '',
    gender: 'male',
    dob: '',
});

const passwords = ref({
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
});

const settings = ref({
    theme: localStorage.getItem('theme') || 'light',
    emailNotifications: true,
    smsNotifications: false,
});

// Computed class for dark/light theme
const themeClass = computed(() => {
    return settings.value.theme === 'dark' ? 'dark' : '';
});

// Watch to persist theme setting in localStorage
watch(() => settings.value.theme, (newTheme) => {
    localStorage.setItem('theme', newTheme);
});

// Handle profile form submission
const handleProfileSubmit = () => {
    console.log('Profile Updated:', profile.value);
    console.log('Password Updated:', passwords.value);
    console.log('Settings Updated:', settings.value);

    // Example of changing password logic
    if (passwords.value.newPassword !== passwords.value.confirmPassword) {
        alert('Passwords do not match!');
    } else {
        alert('Profile and Password Updated!');
    }
};
</script>

<style scoped>
/* Cấu hình theme tối */
.dark {
    background-color: #1a202c;
    color: white;
}

.dark input,
.dark select {
    background-color: #2d3748;
    color: white;
    border-color: #4a5568;
}

button {
    cursor: pointer;
}
</style>