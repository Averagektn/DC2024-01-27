﻿using Forum.Api.Models;
using Forum.Api.Repositories;
using Microsoft.Extensions.Caching.Distributed;
using Newtonsoft.Json;

namespace Forum.Api.Services;

public class CachedCreatorRepository : ICreatorRepository
{
    private readonly ICreatorRepository _creatorRepository;

    private readonly IDistributedCache _cache;

    public CachedCreatorRepository(ICreatorRepository creatorRepository, IDistributedCache cache)
    {
        _creatorRepository = creatorRepository;
        _cache = cache;
    }

    public async Task<List<Creator>> GetAllAsync()
    {
        var cachedCreators = await _cache.GetStringAsync("creators");
        if (!string.IsNullOrEmpty(cachedCreators))
            return JsonConvert.DeserializeObject<List<Creator>>(cachedCreators)!;

        var creators = await _creatorRepository.GetAllAsync();
        await _cache.SetStringAsync("creators", JsonConvert.SerializeObject(creators));
        return creators;
    }

    public async Task<Creator?> GetByIdAsync(long id)
    {
        var cachedCreator = await _cache.GetStringAsync(id.ToString());

        if (!string.IsNullOrEmpty(cachedCreator))
        {
            return JsonConvert.DeserializeObject<Creator>(cachedCreator);
        }

        var creator = await _creatorRepository.GetByIdAsync(id);
            
        if (creator is not null)
            await _cache.SetStringAsync(id.ToString(), JsonConvert.SerializeObject(creator));

        return creator;
    }

    public async Task<Creator> CreateAsync(Creator creatorModel)
    {
        var creator = await _creatorRepository.CreateAsync(creatorModel);

        await _cache.SetStringAsync(creatorModel.Login, JsonConvert.SerializeObject(creatorModel));
        
        return creator;
    }

    public async Task<Creator?> UpdateAsync(long id, Creator updatedCreator)
    {
        await _cache.RemoveAsync(updatedCreator.Login);
        
        var creator = await _creatorRepository.CreateAsync(updatedCreator);
        
        await _cache.SetStringAsync(updatedCreator.Login, JsonConvert.SerializeObject(updatedCreator));

        return creator;
    }

    public async Task<Creator?> DeleteAsync(long id)
    {
        var creator = await _creatorRepository.DeleteAsync(id);
            
        await _cache.RemoveAsync(id.ToString());

        return creator;
    }
}