import {Component, Input} from '@angular/core';
import {PostsResponse} from "../models";

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent {

  @Input()
  categorySlug = ""

  @Input()
  postsResponse : PostsResponse = {
    data: [],
    isFirst: false,
    hasNext: false,
    hasPrevious: false,
    isLast: false,
    pageNumber: 0,
    totalElements: 0,
    totalPages: 0
  }
}
