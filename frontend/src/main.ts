import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import store from './store'
import { library } from '@fortawesome/fontawesome-svg-core'

import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { faBell, faComments, faEnvelope, faEye, faEyeSlash, faImage, faPenToSquare, faStar } from '@fortawesome/free-regular-svg-icons';
import {
    faSearch,
    faBars,
    faUser,
    faBookmark,
    faBook,
    faGear,
    faMoneyBill,
    faRightFromBracket,
    faChevronLeft,
    faChevronRight,
    faInfoCircle,
    faExclamationCircle,
    faExclamationTriangle,
    faCheckCircle,
    faBarsProgress,
    faChartPie,
    faPhone,
    faQuestion,
    faBullhorn,
    faXmark,
    faPen,
    faTrash,
    faFeatherPointed,
    faAnglesUp,
    faAnglesDown,
    faSortUp,
    faSortDown,
    faHeart,
    faList,
    faShareNodes,
    faArrowUpRightFromSquare,
    faArrowUpFromBracket,
    faHouse,
    faQuestionCircle,
    faSquareMinus,
    faSquarePlus,
    faChevronDown,
    faChevronUp,
    faBold,
    faItalic,
    faUnderline,
    faListUl,
    faListOl,
    faQuoteRight,
    faCode,
    faRotateRight,
    faRotateLeft,
    faMinus,
    faPlus,
    faBookOpen, faNewspaper, faSun, faMoon, faKey
} from '@fortawesome/free-solid-svg-icons'
import { faGoogle } from '@fortawesome/free-brands-svg-icons'

library.add(faMoon, faKey , faSun, faNewspaper, faBookOpen, faPlus, faImage,faMinus, faRotateRight, faRotateLeft, faBold, faItalic, faUnderline, faListUl, faListOl, faQuoteRight, faCode, faEnvelope, faChevronDown, faChevronUp,faSquarePlus, faSquareMinus, faQuestionCircle, faHouse, faArrowUpFromBracket, faArrowUpRightFromSquare, faSortUp, faSortDown, faStar, faShareNodes, faList, faComments, faQuestion, faXmark, faHeart, faPen, faTrash, faFeatherPointed, faAnglesUp, faAnglesDown, faPhone, faBullhorn, faChartPie, faPhone, faBell, faPenToSquare, faSearch, faBars, faUser, faBookmark, faBook, faGear, faMoneyBill, faRightFromBracket, faChevronLeft, faChevronRight, faInfoCircle, faExclamationCircle, faCheckCircle, faExclamationTriangle, faEyeSlash, faEye, faGoogle, faBarsProgress)

createApp(App)
    .component('font-awesome-icon', FontAwesomeIcon)
    .use(router)
    .use(store)
    .mount('#app');
