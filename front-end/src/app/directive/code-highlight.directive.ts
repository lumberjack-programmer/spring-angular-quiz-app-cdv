import { Directive, ElementRef, OnInit, Renderer2 } from '@angular/core';

@Directive({
  selector: 'textarea[codeHighlight]'
})
export class CodeHighlightDirective implements OnInit {

  constructor(private el: ElementRef, private render: Renderer2) { }

  ngOnInit(): void {
    const preElement = this.render.createElement('pre');
    const codeElement = this.render.createElement('code');
    const codeText = this.render.createText(this.el.nativeElement.value);
    this.render.appendChild(preElement, codeElement);
    this.render.appendChild(codeElement, codeText);
    this.render.appendChild(this.el.nativeElement.parentNode, preElement);
    this.render.setAttribute(codeElement, 'highlight', 'code');
  }

}
