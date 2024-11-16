<script setup lang="ts">
</script>

<template>
    <h3 class="text-2xl font-bold text-left py-2">User Management</h3>
    <div class="overflow-x-auto relative sm:rounded-lg">
        <table class="w-full text-sm text-left text-gray-500 dark:text-gray-400">
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-gray-700 dark:text-gray-400">
                <tr>
                    <th scope="col" class="py-3 px-6">
                        Novel ID
                    </th>
                    <th scope="col" class="py-3 px-6">
                        Novel Title
                    </th>
                    <th scope="col" class="py-3 px-6">
                        Novel Author
                    </th>
                    <th scope="col" class="py-3 px-6">
                        <span>Edit</span>
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr class="bg-white border-b dark:bg-gray-800 dark:border-gray-700">
                    <th scope="row" class="py-4 px-6 font-medium text-gray-900 whitespace-nowrap dark:text-white">
                        1
                    </th>
                    <td class="py-4 px-6 dark:text-white">
                        Overlord
                    </td>
                    <td class="py-4 px-6 dark:text-white">
                        Maruyama Kugane
                    </td>
                    <td class="py-4 px-6 dark:text-white">
                        <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                            Edit
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
