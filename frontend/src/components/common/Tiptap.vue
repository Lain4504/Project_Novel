<script lang="ts" setup>
import {watch} from "vue";
import {EditorContent, useEditor} from "@tiptap/vue-3";
import StarterKit from "@tiptap/starter-kit";
import Underline from "@tiptap/extension-underline";

const props = defineProps({
  content: {
    type: String,
    required: true,
  },
});
const emit = defineEmits(["update:content"]);

const editor = useEditor({
  content: props.content,
  extensions: [StarterKit, Underline],
  onUpdate({editor}) {
    emit("update:content", editor.getHTML());
  },
  editorProps: {
    attributes: {
      class:
          "border border-gray-300 rounded p-4 min-h-[12rem] max-h-[12rem] overflow-y-auto outline-none prose max-w-none",
    },
  },
});

watch(
    () => props.content,
    (newContent) => {
      if (editor.value && editor.value.getHTML() !== newContent) {
        editor.value.commands.setContent(newContent);
      }
    }
);
</script>

<template>
  <div>
    <section v-if="editor"
             class="text-gray-600 flex items-center flex-wrap gap-x-4 border-t border-r border-l border-gray-300 p-4 bg-gray-100 shadow-sm rounded-t">
      <button :class="{ 'bg-gray-200 rounded': editor.isActive('bold') }"
              :disabled="!editor.can().chain().focus().toggleBold().run()"
              class="p-2 hover:bg-gray-100"
              @click.prevent="editor.chain().focus().toggleBold().run()">
        <font-awesome-icon :icon="['fas', 'bold']"/>
      </button>
      <button :class="{ 'bg-gray-200 rounded': editor.isActive('italic') }"
              :disabled="!editor.can().chain().focus().toggleItalic().run()"
              class="p-2 hover:bg-gray-100"
              @click.prevent="editor.chain().focus().toggleItalic().run()">
        <font-awesome-icon :icon="['fas', 'italic']"/>
      </button>
      <button :class="{ 'bg-gray-200 rounded': editor.isActive('underline') }"
              :disabled="!editor.can().chain().focus().toggleUnderline().run()"
              class="p-2 hover:bg-gray-100"
              @click.prevent="editor.chain().focus().toggleUnderline().run()">
        <font-awesome-icon :icon="['fas', 'underline']"/>
      </button>
      <button :class="{ 'bg-gray-200 rounded': editor.isActive('heading', { level: 1 }) }"
              class="p-2 hover:bg-gray-100"
              @click.prevent="editor.chain().focus().toggleHeading({ level: 1 }).run()">
        H1
      </button>
      <button :class="{ 'bg-gray-200 rounded': editor.isActive('heading', { level: 2 }) }"
              class="p-2 hover:bg-gray-100"
              @click.prevent="editor.chain().focus().toggleHeading({ level: 2 }).run()">
        H2
      </button>
      <button :class="{ 'bg-gray-200 rounded': editor.isActive('bulletList') }"
              class="p-2 hover:bg-gray-100"
              @click.prevent="editor.chain().focus().toggleBulletList().run()">
        <font-awesome-icon :icon="['fas', 'list-ul']"/>
      </button>
      <button :class="{ 'bg-gray-200 rounded': editor.isActive('orderedList') }"
              class="p-2 hover:bg-gray-100"
              @click.prevent="editor.chain().focus().toggleOrderedList().run()">
        <font-awesome-icon :icon="['fas', 'list-ol']"/>
      </button>
      <button :class="{ 'bg-gray-200 rounded': editor.isActive('blockquote') }"
              class="p-2 hover:bg-gray-100"
              @click.prevent="editor.chain().focus().toggleBlockquote().run()">
        <font-awesome-icon :icon="['fas', 'quote-right']"/>
      </button>
      <button :class="{ 'bg-gray-200 rounded': editor.isActive('code') }"
              class="p-2 hover:bg-gray-100"
              @click.prevent="editor.chain().focus().toggleCode().run()">
        <font-awesome-icon :icon="['fas', 'code']"/>
      </button>
      <button class="p-2 hover:bg-gray-100"
              @click.prevent="editor.chain().focus().setHorizontalRule().run()">
        <font-awesome-icon :icon="['fas', 'minus']"/>
      </button>
      <button :disabled="!editor.can().chain().focus().undo().run()"
              class="p-2 hover:bg-gray-100 disabled:text-gray-400"
              @click.prevent="editor.chain().focus().undo().run()">
        <font-awesome-icon :icon="['fas', 'rotate-left']"/>
      </button>
      <button :disabled="!editor.can().chain().focus().redo().run()"
              class="p-2 hover:bg-gray-100 disabled:text-gray-400"
              @click.prevent="editor.chain().focus().redo().run()">
        <font-awesome-icon :icon="['fas', 'rotate-right']"/>
      </button>
    </section>
    <editor-content :editor="editor" class="bg-white border border-gray-300 rounded-b shadow-sm"/>
  </div>
</template>z