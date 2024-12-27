import {createApp} from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'
import store from './store'
import {library} from '@fortawesome/fontawesome-svg-core'

import {FontAwesomeIcon} from '@fortawesome/vue-fontawesome'
import {
    faBell,
    faComments,
    faEnvelope,
    faEye,
    faEyeSlash,
    faImage,
    faPenToSquare,
    faStar
} from '@fortawesome/free-regular-svg-icons';
import {
    faAnglesDown,
    faAnglesLeft,
    faAnglesRight,
    faAnglesUp,
    faArrowUpFromBracket,
    faArrowUpRightFromSquare,
    faBars,
    faBarsProgress,
    faBold,
    faBook,
    faBookmark,
    faBookOpen,
    faBullhorn,
    faChartPie,
    faCheckCircle,
    faChevronDown,
    faChevronLeft,
    faChevronRight,
    faChevronUp,
    faCode,
    faExclamationCircle,
    faExclamationTriangle,
    faFeatherPointed,
    faFont,
    faGear,
    faHeart,
    faHouse,
    faInfoCircle,
    faItalic,
    faKey,
    faList,
    faListOl,
    faListUl,
    faMinus,
    faMoneyBill,
    faMoon,
    faNewspaper,
    faPen,
    faPhone,
    faPlus,
    faQuestion,
    faQuestionCircle,
    faQuoteRight,
    faRightFromBracket,
    faRotateLeft,
    faRotateRight,
    faSearch,
    faShareNodes,
    faSortDown,
    faSortUp,
    faSquareMinus,
    faSquarePlus,
    faSun,
    faTrash,
    faUnderline,
    faUser,
    faXmark
} from '@fortawesome/free-solid-svg-icons'
import {faGoogle} from '@fortawesome/free-brands-svg-icons'
import 'ant-design-vue/dist/reset.css';
import Antd from 'ant-design-vue';

library.add(faFont, faAnglesLeft, faAnglesRight, faMoon, faKey, faSun, faNewspaper, faBookOpen, faPlus, faImage, faMinus, faRotateRight, faRotateLeft, faBold, faItalic, faUnderline, faListUl, faListOl, faQuoteRight, faCode, faEnvelope, faChevronDown, faChevronUp, faSquarePlus, faSquareMinus, faQuestionCircle, faHouse, faArrowUpFromBracket, faArrowUpRightFromSquare, faSortUp, faSortDown, faStar, faShareNodes, faList, faComments, faQuestion, faXmark, faHeart, faPen, faTrash, faFeatherPointed, faAnglesUp, faAnglesDown, faPhone, faBullhorn, faChartPie, faPhone, faBell, faPenToSquare, faSearch, faBars, faUser, faBookmark, faBook, faGear, faMoneyBill, faRightFromBracket, faChevronLeft, faChevronRight, faInfoCircle, faExclamationCircle, faCheckCircle, faExclamationTriangle, faEyeSlash, faEye, faGoogle, faBarsProgress)

createApp(App)
    .component('font-awesome-icon', FontAwesomeIcon)
    .use(router)
    .use(store)
    .use(Antd)
    .mount('#app');
