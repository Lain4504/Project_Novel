<template>
  <a-back-top>
    <div class="ant-back-top-inner">
      <a-icon>
        <VerticalAlignTopOutlined />
      </a-icon>
    </div>
  </a-back-top>
</template>

<script lang="ts" setup>
import { VerticalAlignTopOutlined } from '@ant-design/icons-vue';
</script>

<style scoped>
.ant-back-top-inner {
  height: 40px;
  width: 40px;
  line-height: 40px;
  border-radius: 4px;
  background-color: #1088e9;
  color: #fff;
  text-align: center;
  font-size: 14px;
}
</style>