import { createApp } from 'vue';
import axios from 'axios';

import 'primevue/resources/themes/mdc-dark-indigo/theme.css';
import 'primevue/resources/primevue.min.css';
import 'primeicons/primeicons.css';

import Bucki from './Bucki.vue';


const app = createApp(Bucki);
app.config.globalProperties.axios=axios;
app.mount('#bucki');
