import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhblogBlogModule } from './blog/blog.module';
import { JhblogEntryModule } from './entry/entry.module';
import { JhblogTagModule } from './tag/tag.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        JhblogBlogModule,
        JhblogEntryModule,
        JhblogTagModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhblogEntityModule {}
