import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IMatchResult, NewMatchResult } from '../match-result.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IMatchResult for edit and NewMatchResultFormGroupInput for create.
 */
type MatchResultFormGroupInput = IMatchResult | PartialWithRequiredKeyOf<NewMatchResult>;

type MatchResultFormDefaults = Pick<NewMatchResult, 'id'>;

type MatchResultFormGroupContent = {
  id: FormControl<IMatchResult['id'] | NewMatchResult['id']>;
  technicalMatch: FormControl<IMatchResult['technicalMatch']>;
  aiTechnicalAffinity: FormControl<IMatchResult['aiTechnicalAffinity']>;
  aiMotivationalAffinity: FormControl<IMatchResult['aiMotivationalAffinity']>;
  aiExperienceRelevance: FormControl<IMatchResult['aiExperienceRelevance']>;
  aiComment: FormControl<IMatchResult['aiComment']>;
  createdAt: FormControl<IMatchResult['createdAt']>;
  project: FormControl<IMatchResult['project']>;
  developer: FormControl<IMatchResult['developer']>;
};

export type MatchResultFormGroup = FormGroup<MatchResultFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class MatchResultFormService {
  createMatchResultFormGroup(matchResult: MatchResultFormGroupInput = { id: null }): MatchResultFormGroup {
    const matchResultRawValue = {
      ...this.getFormDefaults(),
      ...matchResult,
    };
    return new FormGroup<MatchResultFormGroupContent>({
      id: new FormControl(
        { value: matchResultRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      technicalMatch: new FormControl(matchResultRawValue.technicalMatch, {
        validators: [Validators.required],
      }),
      aiTechnicalAffinity: new FormControl(matchResultRawValue.aiTechnicalAffinity),
      aiMotivationalAffinity: new FormControl(matchResultRawValue.aiMotivationalAffinity),
      aiExperienceRelevance: new FormControl(matchResultRawValue.aiExperienceRelevance),
      aiComment: new FormControl(matchResultRawValue.aiComment),
      createdAt: new FormControl(matchResultRawValue.createdAt, {
        validators: [Validators.required, Validators.maxLength(50)],
      }),
      project: new FormControl(matchResultRawValue.project, {
        validators: [Validators.required],
      }),
      developer: new FormControl(matchResultRawValue.developer, {
        validators: [Validators.required],
      }),
    });
  }

  getMatchResult(form: MatchResultFormGroup): IMatchResult | NewMatchResult {
    return form.getRawValue() as IMatchResult | NewMatchResult;
  }

  resetForm(form: MatchResultFormGroup, matchResult: MatchResultFormGroupInput): void {
    const matchResultRawValue = { ...this.getFormDefaults(), ...matchResult };
    form.reset(
      {
        ...matchResultRawValue,
        id: { value: matchResultRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): MatchResultFormDefaults {
    return {
      id: null,
    };
  }
}
