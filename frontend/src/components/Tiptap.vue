<script setup lang="ts">
import { useEditor, EditorContent } from '@tiptap/vue-3';
import StarterKit from '@tiptap/starter-kit';
import Underline from '@tiptap/extension-underline';
import Image from '@tiptap/extension-image';

const props = defineProps({
  content: String,
});
const emit = defineEmits(['update:content']);

const editor = useEditor({
  content: props.content,  // Sử dụng giá trị content từ props
  extensions: [
    StarterKit,
    Underline,
  ],
  onUpdate({ editor }) {
    console.log('Current Tiptap Content:', editor.getHTML());
    emit('update:content', editor.getHTML());  // Phát sự kiện khi có thay đổi
  },
  editorProps: {
    attributes: {
      class: 'border border-gray-400 rounded p-4 min-h-[12rem] max-h-[12rem] overflow-y-auto outline-none prose max-w-none',
    },
  },
});
</script>

<template>
    <div> 
        <section v-if="editor"
            class="buttons text-gray-600 flex items-center flex-wrap gap-x-4 border-t border-r border-l border-gray-400 p-4">
            <button @click="editor.chain().focus().toggleBold().run()"
                :disabled="!editor.can().chain().focus().toggleBold().run()"
                :class="{ 'bg-gray-200 rounded': editor.isActive('bold') }">
                <font-awesome-icon :icon="['fas', 'bold']" />
            </button>
            <button @click="editor.chain().focus().toggleItalic().run()"
                :disabled="!editor.can().chain().focus().toggleItalic().run()"
                :class="{ 'bg-gray-200 rounded': editor.isActive('italic') }">
                <font-awesome-icon :icon="['fas', 'italic']" />
            </button>
            <button @click="editor.chain().focus().toggleUnderline().run()"
                :disabled="!editor.can().chain().focus().toggleUnderline().run()"
                :class="{ 'bg-gray-200 rounded': editor.isActive('underline') }" class="p-1">
                <font-awesome-icon :icon="['fas', 'underline']" />
            </button>
            <button @click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
                :class="{ 'bg-gray-200 rounded': editor.isActive('heading', { level: 1 }) }" class="p-1">
                H1
            </button>
            <button @click="editor.chain().focus().toggleHeading({ level: 2 }).run()"
                :class="{ 'bg-gray-200 rounded': editor.isActive('heading', { level: 2 }) }" class="p-1">
                H2
            </button>
            <button @click="editor.chain().focus().toggleBulletList().run()"
                :class="{ 'bg-gray-200 rounded': editor.isActive('bulletList') }" class="p-1">
                 <font-awesome-icon :icon="['fas', 'list-ul']" />
            </button>
            <button @click="editor.chain().focus().toggleOrderedList().run()"
                :class="{ 'bg-gray-200 rounded': editor.isActive('orderedList') }" class="p-1">
                 <font-awesome-icon :icon="['fas', 'list-ol']" />
            </button>
            <button @click="editor.chain().focus().toggleBlockquote().run()"
                :class="{ 'bg-gray-200 rounded': editor.isActive('blockquote') }" class="p-1">
                <font-awesome-icon :icon="['fas', 'quote-right']" />
            </button>
            <button @click="editor.chain().focus().toggleCode().run()"
                :class="{ 'bg-gray-200 rounded': editor.isActive('code') }"
                class="p-1">
                <font-awesome-icon :icon="['fas', 'code']" />
            </button>
            <button @click="editor.chain().focus().setHorizontalRule().run()"
                class="p-1">
                <font-awesome-icon :icon="['fas', 'minus']" />
            </button>
            <button @click="editor.chain().focus().undo().run()"
            :disabled="!editor.can().chain().focus().undo().run()"
            class="p-1 disabled:text-gray-400">
            <font-awesome-icon :icon="['fas', 'rotate-left']" />
            </button>
            <button @click="editor.chain().focus().redo().run()" 
            :disabled="!editor.can().chain().focus().redo().run()"
            class="p-1 disabled:text-gray-400">
            <font-awesome-icon :icon="['fas', 'rotate-right']" />
            </button>
        </section>
        <editor-content :editor="editor" />
    </div>
</template>
