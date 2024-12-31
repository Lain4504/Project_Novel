<script lang="ts" setup>
import {ref} from 'vue';
import bannerUrl from '@/assets/banner.webp';

const url = ref('');
const query = (searchUrl: string) => {
  console.log('Searching for:', searchUrl);
};
</script>
<template>
  <div
      :style="{ background: `rgba(0, 0, 0, .25) url(${bannerUrl})` }"
  >
    <div id="banner">
      <a-typography-title
          style="
          text-align: center;
          font-size: 3em;
          color: white;
          filter: drop-shadow(0.05em 0.05em black);
        "
      >
        HawkNovel
      </a-typography-title>
      <div class="search-bar">
        <a-input-group compact style="width: 100%; display: flex;">
          <a-input
              v-model:value="url"
              size="large"
              placeholder="Nhập tên tác phẩm..."
              :style="{ flex: 1}"
              @keyup.enter="query(url)"
          />
          <a-button
              size="large"
              type="primary"
              style="width: 100px;"
              @click="query(url)"
          >
            Tìm kiếm
          </a-button>
        </a-input-group>
      </div>
    </div>
  </div>
</template>

<style scoped>
#banner {
  padding-top: 20px;
  padding-bottom: 50px;
}

.search-bar {
  display: flex;
  justify-content: center;
  max-width: 800px;
  margin: 0 auto;
}

@media only screen and (max-width: 840px) {
  #banner {
    padding-top: 10px;
    padding-bottom: 35px;
  }

  .search-bar {
    margin: 0 10px;
  }
}
</style>