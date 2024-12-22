<script setup lang="ts">
import {ref, computed, onMounted} from 'vue';
import {getUserProfile, updateUserProfile} from "@/api/user";
import {changePassword} from "@/api/auth";
import store from "@/store";
import Banner from "@/components/home/Banner.vue";
import Tiptap from "@/components/common/Tiptap.vue";
import {Button, Input, Select, Form, message, Tabs} from 'ant-design-vue';

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
  formData.append("profile", new Blob([JSON.stringify(profileData)], {type: "application/json"}));
  if (selectedImage.value) {
    formData.append("image", selectedImage.value);
  }
  try {
    await updateUserProfile(profile.value.id, formData);
    message.success('Profile Updated Successfully!');
  } catch (error) {
    console.error(error);
    message.error('Failed to update profile.');
  }
}
const handleChangePassword = async () => {
  if (passwords.value.newPassword !== passwords.value.confirmPassword) {
    message.error('New password and confirm password do not match!');
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

    message.success('Password Changed Successfully!');
  } catch (error) {
    console.error(error);
    message.error('Failed to change password.');
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
      <Tabs v-model:activeKey="activeTab" class="custom-tabs">
        <Tabs.TabPane key="profile" tab="Profile">
          <div class="p-6 space-y-6">
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

            <Form @submit.prevent="handleProfileSubmit" class="grid md:grid-cols-2 gap-6">
              <div class="space-y-4">
                <Form.Item label="Username">
                  <Input v-model:value="profile.username"/>
                </Form.Item>
                <Form.Item label="Gender">
                  <Select v-model:value="profile.gender">
                    <Select.Option value="male">Male</Select.Option>
                    <Select.Option value="female">Female</Select.Option>
                    <Select.Option value="other">Other</Select.Option>
                  </Select>
                </Form.Item>
              </div>
              <div class="space-y-4">
                <Form.Item label="Date of Birth">
                  <Input v-model:value="formattedDateOfBirth" type="date"/>
                </Form.Item>
              </div>
              <div class="md:col-span-2 space-y-4">
                <Form.Item label="Bio">
                  <Tiptap :content="profile.bio" @update:content="profile.bio = $event"/>
                </Form.Item>
              </div>
              <div class="md:col-span-2 flex justify-end">
                <Button type="primary" html-type="submit">
                  Update Profile
                </Button>
              </div>
            </Form>
          </div>
        </Tabs.TabPane>
        <Tabs.TabPane key="security" tab="Security">
          <div class="p-6 space-y-6">
            <Form @submit.prevent="handleChangePassword" class="space-y-4">
              <Form.Item label="Current Password" class="form-item-stacked" :labelCol="{ span: 6 }"
                         :wrapperCol="{ span: 18 }">
                <Input.Password v-model:value="passwords.oldPassword"/>
              </Form.Item>
              <Form.Item label="New Password" class="form-item-stacked" :labelCol="{ span: 6 }"
                         :wrapperCol="{ span: 18 }">
                <Input.Password v-model:value="passwords.newPassword"/>
              </Form.Item>
              <Form.Item label="Confirm New Password" class="form-item-stacked" :labelCol="{ span: 6 }"
                         :wrapperCol="{ span: 18 }">
                <Input.Password v-model:value="passwords.confirmPassword"/>
              </Form.Item>
              <div class="flex justify-end">
                <Button type="primary" html-type="submit">
                  Change Password
                </Button>
              </div>
            </Form>
          </div>
        </Tabs.TabPane>
        <Tabs.TabPane key="theme" tab="Theme">
          <div class="p-6 space-y-6">
            <div class="flex justify-between items-center">
              <div>
                <h3 class="text-lg font-medium dark:text-white">Theme</h3>
                <p class="text-sm text-gray-500">Switch between light and dark modes</p>
              </div>
              <Button @click="toggleTheme" shape="circle" icon="sun" v-if="!isDarkMode"/>
              <Button @click="toggleTheme" shape="circle" icon="moon" v-else/>
            </div>
          </div>
        </Tabs.TabPane>
      </Tabs>
    </div>
  </div>
</template>

<style scoped>
.custom-tabs {
  padding: 16px; /* Add padding to the tabs */
}

.custom-tabs .ant-tabs-nav {
  margin-bottom: 16px;
}

.custom-tabs .ant-tabs-tab {
  padding: 12px 16px;
  font-size: 16px;
}

.custom-tabs .ant-tabs-tab-active {
  color: #1890ff;
  font-weight: bold;
}

.custom-tabs .ant-tabs-ink-bar {
  background-color: #1890ff;
}

.form-item-stacked .ant-form-item-label {
  display: block;
  margin-bottom: 8px;
}

.form-item-stacked .ant-form-item-control {
  display: block;
}
</style>